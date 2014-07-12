package net.csf.controller;

import java.io.IOException;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.PostConstruct;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.csf.annotation.ServiceMethod;
import net.csf.config.CsfConfig;
import net.csf.config.CsfRemoteBeanDefinitionParser;
import net.csf.config.RemoteServiceConfig;
import net.csf.exception.BusinessException;
import net.csf.request.IRequestParam;
import net.csf.request.RequestMessage;
import net.csf.response.ResponseMessage;
import net.csf.service.IHttpCommandHandler;
import net.csf.service.IService;
import net.csf.service.MethodService;
import net.csf.service.RemoteService;
import net.csf.utils.EasyApplicationContextUtils;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.core.annotation.AnnotationUtils;
import org.springframework.stereotype.Service;
import org.springframework.util.ClassUtils;
import org.springframework.util.ReflectionUtils;

/**
 * 
 * 服务管理器
 * 
 * @author zhaoli
 * @date 2011 Dec 15, 2011 10:50:56 AM
 */
@Service
public class ServiceManager implements ApplicationContextAware{
  protected Log log = (Log) LogFactory.getLog(this.getClass());
  
  private ApplicationContext context = null;
  
  @Autowired(required = false)
  private List<IHttpCommandHandler> cmdHandlerList;
  @Autowired
  private CsfConfig csfConfig;
  
  private Map<String, IService> serviceMap = new HashMap<String, IService>();

  @PostConstruct
  public void init() {
    registHttpService();
    registRemoteService();
  }
  
  /**
   * 服务调用入口
   * @param request
   * @param response
   * @throws IOException 
   */
  public void handleRequest(HttpServletRequest request, HttpServletResponse response) throws IOException{
    ServiceContext context = new ServiceContext();
    context.setRequest(request);
    context.setResponse(response);
    
    IRequestParam param = csfConfig.getRequestParser().parser(context);
    context.setRequestParam(param);
    
    try {
      IService service = csfConfig.getServiceRouter().routeService(context);  //选择处理器
      context.setService(service);
      
      Object result = service.doService(context);
      context.setResult(result);
      
    } catch (BusinessException e) {
      log.error(e, e);
      context.setResultCode(e.getErrorCode());
      context.setError(e.getErrorDesc());
    }
    csfConfig.getResponseParser().parser(context);
  }
  
  /**
   * 注册远程服务
   */
  private void registRemoteService() {
    log.info("开始注册远程服务");
    List<RemoteServiceConfig> list = CsfRemoteBeanDefinitionParser.getInstance().getRemoteConfigList();
    for(RemoteServiceConfig config:list){
      RemoteService s = new RemoteService(config.getRemote_url(), config.getCommand(), config.getRemote_command(), config.getDesc());
      serviceMap.put(config.getCommand(), s);
      
      if (log.isDebugEnabled()) {
        log.debug("注册远程服务：" + config.getRemote_url() + "#" + config.getCommand());
      }
    }
  }

  /**
   * 注册服务
   */
  private void registHttpService(){
    log.info("开始注册HTTP服务");
    String[] beanNames = context.getBeanNamesForType(Object.class);
    for (final String beanName : beanNames) {
      log.info(beanName);
        Class<?> handlerType = context.getType(beanName);
      ReflectionUtils.doWithMethods(handlerType,
          new ReflectionUtils.MethodCallback() {
            public void doWith(Method method) throws IllegalArgumentException, IllegalAccessException {
              ReflectionUtils.makeAccessible(method);
              
              if (method.getParameterTypes().length != 1) {//handler method's parameter
                log.error(method.getDeclaringClass().getName() + "." + method.getName() +"的服务参数类型只能为1个");
                return;
              }else{
                Class<?> paramType = method.getParameterTypes()[0];
                if (!ClassUtils.isAssignable(IRequestParam.class, paramType)) {
                    log.error(method.getDeclaringClass().getName() + "." + method.getName()
                            + "的入参必须是" + ServiceParam.class.getName());
                    return;
                }
              } 
              
              ServiceMethod httpService = method.getAnnotation(ServiceMethod.class);
              MethodService httpServerService = new MethodService(httpService.command(), context.getBean(beanName), method);
              httpServerService.setServiceDesc(httpService.desc());
              serviceMap.put(httpService.command(), httpServerService);
              
              if (log.isDebugEnabled()) {
                log.debug("注册服务方法：" + method.getDeclaringClass().getCanonicalName() + "#" + method.getName() + "(..)");
              }
            }
          }, new ReflectionUtils.MethodFilter() {
            public boolean matches(Method method) {
              return AnnotationUtils.findAnnotation(method, ServiceMethod.class) != null;
            }
          });
    }
  }

  /***
   * 处理收到的消息
   */
  public ResponseMessage handle(RequestMessage message, String format) throws BusinessException {
    IService service = getIService(message.getHttpCommand());
    ResponseMessage response = (ResponseMessage)service.doService(message, format);
    return response;
  }
  
  /**
   * 根据COMMAND查询服务
   * @param command
   * @return
   * @throws BusinessException
   */
  public IService getIService(String command) throws BusinessException{
    IService service = serviceMap.get(command);
    if(service != null){
      return service;
    }
    
    log.error("没有找到HTTP命令【" + command + "】相应的处理器，请检查业务逻辑");
    throw new BusinessException(1, "没有找到HTTP命令【" + command + "】相应的处理器，请检查业务逻辑",
        "没有找到HTTP命令【" + command + "】相应的处理器，请检查业务逻辑");
  }

  public void setApplicationContext(ApplicationContext context) throws BeansException {
    this.context = context;
    EasyApplicationContextUtils.setApplicationContext(context);
  }
  
  public Map<String, IService> getHttpServices(){
    return serviceMap;
  }
}
