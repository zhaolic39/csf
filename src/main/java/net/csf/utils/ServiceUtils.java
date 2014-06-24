package net.csf.utils;

import java.lang.reflect.InvocationTargetException;
import java.util.HashMap;
import java.util.Map;

import net.csf.controller.ServiceManager;
import net.csf.conventor.MessageConventor;
import net.csf.exception.BusinessException;
import net.csf.request.CSFRequest;
import net.csf.request.RequestMessage;
import net.csf.response.CSFResponse;
import net.csf.response.ResponseMessage;
import net.csf.service.IService;
import net.csf.service.MethodService;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**
 * <p>Title: 服务调用类 </p>
 * <p>Description:</p>
 *
 * @author zhaoli
 * @version 1.0 2013-2-21
 */
public class ServiceUtils {
  private final static  Log logger = (Log) LogFactory.getLog(ServiceUtils.class);
  
  /**
   * 调用内部服务。
   * 直接反射调用@ServiceMethod服务，需要正确的参数类型与返回泛型。
   * @param command
   * @param request
   * @param response_class
   * @return
   * @throws BusinessException 
   */
  public static <T> T callInternal(String command, Object request, Class<T> response_class) throws BusinessException{
    ServiceManager cmdManger = (ServiceManager) EasyApplicationContextUtils.getBeanByType(ServiceManager.class);
    IService s = cmdManger.getIService(command);
    if(s instanceof MethodService){ //是ServiceMethod注册的内部服务
      MethodService ms = (MethodService)s;
      try {
        Object o = ms.reflectInvoke(request);
        if(o != null)
          return response_class.cast(o);
        else{
          return null;
        }
      }
      catch (InvocationTargetException e) {
        if(e.getTargetException() instanceof BusinessException){
          BusinessException httpe = (BusinessException)e.getTargetException();
          throw new BusinessException(httpe.getErrorCode(), httpe.getErrorDesc(), httpe.getErrorResult());
        }
        logger.error(e, e);
        throw new BusinessException("1", e.getTargetException().toString(), e.getTargetException().toString());
      } catch (Exception e) {
        logger.error(e, e);
        throw new BusinessException("1", e.getMessage(), e.getMessage());
      }
    }
    else{
      throw new BusinessException("1", "命令【" + command + "】不是ServiceMethod服务，无法内部调用",
          "命令【" + command + "】不是ServiceMethod服务，无法内部调用");
    }
  }
  
  /**
   * 调用服务
   * @param request
   * @param format
   * @return
   * @throws BusinessException
   */
  public static ResponseMessage callService(RequestMessage request, String format) throws BusinessException{
    ServiceManager cmdManger = (ServiceManager) EasyApplicationContextUtils.getBeanByType(ServiceManager.class);
    ResponseMessage response = cmdManger.handle(request, format);
    if(!"0".equals(response.getRespCode())){  //手动抛错
      throw new BusinessException(response.getRespCode(), response.getRespDesc(), response.getRespResult());
    }
    return response;
  }
  
  /**
   * 调用服务
   * @param command
   * @param request
   * @param response_class
   * @return
   * @throws BusinessException
   */
  public static <T> T callService(String command, Object request, Class<T> response_class) throws BusinessException{
    String body = MessageConventor.getInstacne(Constants.JSON).marshaller(request.getClass(), request);
    RequestMessage reqMsg = new RequestMessage();
    reqMsg.setHttpCommand(command);
    reqMsg.setHttpCommandBody(body);
    
    ResponseMessage respMsg = callService(reqMsg, Constants.JSON);
    Object respObj = MessageConventor.getInstacne(Constants.JSON).unmarshaller(response_class, respMsg.getRespBody());
    return response_class.cast(respObj);
  }
  
  /**
   * 服务内部调用通用方法
   * @param command
   * @param request
   * @return
   * @throws BusinessException
   */
  public static Map<String, Object> callService(String command, Map<String, Object> request) throws BusinessException{
    String body = MessageConventor.getInstacne(Constants.JSON).marshaller(request.getClass(), request);
    RequestMessage reqMsg = new RequestMessage();
    reqMsg.setHttpCommand(command);
    reqMsg.setHttpCommandBody(body);
    
    Map<String, Object> map = new HashMap<String, Object>();
    map = callService(command, request, map.getClass());
    return map;
  }
  
  /**
   * CSF服务调用通用方法，无抛错
   * @param request
   * @return
   */
  public static CSFResponse<Map<String, Object>> callCSFService(CSFRequest request){
    CSFResponse<Map<String, Object>> response = new CSFResponse<Map<String, Object>>();
    try {
      Map<String, Object> res = callService(request.getCommand(), request.getBody());
      response.setBody(res);
    } catch (BusinessException e) {
      response.setRespCode(e.getErrorCode());
      response.setRespDesc(e.getErrorDesc());
      response.setRespResult(e.getErrorResult());
    }
    return response;
  }
  
  /**
   * CSF服务通用调用方法，返回response_class类型body
   * @param request
   * @param response_class
   * @return
   */
  public static <T> CSFResponse<T> callCSFService(CSFRequest request, Class<T> response_class){
    CSFResponse<T> response = new CSFResponse<T>();
    try {
      T res = callService(request.getCommand(), request.getBody(), response_class);
      response.setBody(res);
    } catch (BusinessException e) {
      response.setRespCode(e.getErrorCode());
      response.setRespDesc(e.getErrorDesc());
      response.setRespResult(e.getErrorResult());
    }
    return response;
  }
  
}
