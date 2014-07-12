package net.csf.router;

import java.util.Map;

import net.csf.controller.ServiceContext;
import net.csf.exception.BusinessException;
import net.csf.service.IService;

/**
 * 服务路由器
 * @author zhaolic39
 */
public interface ServiceRouter {
  public void initServices(Map<String, IService> serviceMap);
  
  public IService routeService(ServiceContext context) throws BusinessException;
}
