package net.csf.parser;

import net.csf.controller.ServiceContext;
import net.csf.request.IRequestParam;
import net.csf.request.RestRequestParam;

/**
 * rest服务请求
 * @author zhaolic39
 */
public class RestRequestParser implements RequestParser{

  public IRequestParam parser(ServiceContext context) {
    RestRequestParam param = new RestRequestParam();
    param.setHttpRequest(context.getRequest());
    
    
    return param;
  }

}
