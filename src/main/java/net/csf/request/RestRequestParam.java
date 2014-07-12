package net.csf.request;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.fileupload.FileItem;

/**
 * rest请求服务参数
 * @author zhaolic39
 */
public class RestRequestParam implements IRequestParam{
  public static final String PARAM_METHOD = "method";
  
  private HttpServletRequest request;
  
  public String getString(String param){
    return request.getParameter(param);
  }
  
  public void setHttpRequest(HttpServletRequest request) {
    this.request = request;
  }
  
  public HttpServletRequest getHttpRequest(){
    return request;
  }

  public void setMessageBody(String messageBody) {
    
  }

  public void setFileItem(FileItem fileItem) {
    
  }

}
