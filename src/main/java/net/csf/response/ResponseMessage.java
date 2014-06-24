package net.csf.response;


/**
 * <p>Title: 服务返回消息 </p>
 * <p>Description:</p>
 *
 * @author zhaoli
 * @version 1.0 2013-1-31
 */
public class ResponseMessage {
  private String respResult = "";
  
  private String respDate = "";

  private String respCode = "";
  
  private String respDesc = "";
  
  private String respBody = "";

  public String getRespResult() {
    return respResult;
  }

  public void setRespResult(String respResult) {
    this.respResult = respResult;
  }

  public String getRespDate() {
    return respDate;
  }

  public void setRespDate(String respDate) {
    this.respDate = respDate;
  }

  public String getRespCode() {
    return respCode;
  }

  public void setRespCode(String respCode) {
    this.respCode = respCode;
  }

  public String getRespDesc() {
    return respDesc;
  }

  public void setRespDesc(String respDesc) {
    this.respDesc = respDesc;
  }

  public String getRespBody() {
    return respBody;
  }

  public void setRespBody(String respBody) {
    this.respBody = respBody;
  }
  
  
}
