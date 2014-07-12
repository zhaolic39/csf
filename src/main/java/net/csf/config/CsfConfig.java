package net.csf.config;

import net.csf.parser.RequestParser;
import net.csf.parser.ResponseParser;
import net.csf.router.ServiceRouter;

import org.springframework.beans.factory.DisposableBean;
import org.springframework.beans.factory.InitializingBean;

/**
 * CSF基础配置
 * @author zhaolic39
 */
public class CsfConfig implements InitializingBean, DisposableBean{
  private RequestParser requestParser;
  private ServiceRouter serviceRouter;
  private ResponseParser responseParser;
  
  public ResponseParser getResponseParser() {
    return responseParser;
  }

  public void setResponseParser(ResponseParser responseParser) {
    this.responseParser = responseParser;
  }

  public ServiceRouter getServiceRouter() {
    return serviceRouter;
  }

  public void setServiceRouter(ServiceRouter serviceRouter) {
    this.serviceRouter = serviceRouter;
  }

  public RequestParser getRequestParser() {
    return requestParser;
  }

  public void setRequestParser(RequestParser requestParser) {
    this.requestParser = requestParser;
  }

  public void afterPropertiesSet() throws Exception {
    System.out.println("afterPropertiesSet");
  }

  public void destroy() throws Exception {
    
  }
  
}
