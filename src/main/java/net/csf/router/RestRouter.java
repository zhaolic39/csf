package net.csf.router;

import java.util.Map;

import net.csf.controller.ServiceContext;
import net.csf.exception.BusinessException;
import net.csf.request.RestRequestParam;
import net.csf.service.IService;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**
 * rest服务路由器
 * @author zhaolic39
 */
public class RestRouter implements ServiceRouter {
  private Log log = (Log) LogFactory.getLog(this.getClass());
  
  private Map<String, IService> serviceMap = null;
  
  public IService routeService(ServiceContext context) throws BusinessException {
    RestRequestParam param = (RestRequestParam)context.getRequestParam();
    String command = param.getString(RestRequestParam.PARAM_METHOD);
    
    IService service = serviceMap.get(command);
    if(service != null){
      return service;
    }
    
    log.error("没有找到HTTP命令【" + command + "】相应的处理器，请检查业务逻辑");
    throw new BusinessException("1", "没有找到HTTP命令【" + command + "】相应的处理器，请检查业务逻辑",
        "没有找到HTTP命令【" + command + "】相应的处理器，请检查业务逻辑");
  }

  public void initServices(Map<String, IService> serviceMap) {
    this.serviceMap = serviceMap;
  }

}
