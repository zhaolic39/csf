package net.csf.request;

import org.apache.commons.fileupload.FileItem;
import org.apache.commons.lang.builder.ReflectionToStringBuilder;

/**
 * <p>Title: 服务请求消息 </p>
 * <p>Description:</p>
 *
 * @author zhaoli
 * @version 1.0 2013-1-31
 */
public class RequestMessage {
  private String httpCommand = "";
  
  private String httpCommandBody = "";
  
  private String requestDate = "";
  
  private String verifyCode = "";
  
  private FileItem fileItem;

  public String getHttpCommand() {
    return httpCommand;
  }

  public void setHttpCommand(String httpCommand) {
    this.httpCommand = httpCommand;
  }

  public String getHttpCommandBody() {
    return httpCommandBody;
  }

  public void setHttpCommandBody(String httpCommandBody) {
    this.httpCommandBody = httpCommandBody;
  }

  public String getRequestDate() {
    return requestDate;
  }

  public void setRequestDate(String requestDate) {
    this.requestDate = requestDate;
  }

  public String getVerifyCode() {
    return verifyCode;
  }

  public void setVerifyCode(String verifyCode) {
    this.verifyCode = verifyCode;
  }

  public FileItem getFileItem() {
    return fileItem;
  }

  public void setFileItem(FileItem fileItem) {
    this.fileItem = fileItem;
  }
  
  public String toString(){
    return ReflectionToStringBuilder.toString(this);
  }
}
