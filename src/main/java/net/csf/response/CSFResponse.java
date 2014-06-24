package net.csf.response;


/**
 * <p>Title: CSF服务响应参数，body为JSON转换的复杂MAP对象 </p>
 * <p>Description:</p>
 *
 * @author zhaoli
 * @version 1.0 2013-10-24
 */
public class CSFResponse<T>{
  private String respResult;
  
  private String respDate;

  private String respCode = "0";
  
  private String respDesc;
  
  private T body;

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

  public T getBody() {
    return body;
  }

  public void setBody(T body) {
    this.body = body;
  }
}
