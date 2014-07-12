package net.csf.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.csf.request.IRequestParam;
import net.csf.response.IResponseParam;
import net.csf.service.IService;

/**
 * 服务调用上下文
 * @author zhaolic39
 */
public class ServiceContext {
  private Object result;
  private HttpServletResponse response;
  private HttpServletRequest request;
  private IService service;
  private IRequestParam requestParam;
  private IResponseParam responseParam;
  private int resultCode = 0;
  private String error = "";
  
  public int getResultCode() {
    return resultCode;
  }
  public void setResultCode(int resultCode) {
    this.resultCode = resultCode;
  }
  public String getError() {
    return error;
  }
  public void setError(String error) {
    this.error = error;
  }
  public IRequestParam getRequestParam() {
    return requestParam;
  }
  public void setRequestParam(IRequestParam requestParam) {
    this.requestParam = requestParam;
  }
  public IResponseParam getResponseParam() {
    return responseParam;
  }
  public void setResponseParam(IResponseParam responseParam) {
    this.responseParam = responseParam;
  }
  public Object getResult() {
    return result;
  }
  public void setResult(Object result) {
    this.result = result;
  }
  public HttpServletResponse getResponse() {
    return response;
  }
  public void setResponse(HttpServletResponse response) {
    this.response = response;
  }
  public HttpServletRequest getRequest() {
    return request;
  }
  public void setRequest(HttpServletRequest request) {
    this.request = request;
  }
  public IService getService() {
    return service;
  }
  public void setService(IService service) {
    this.service = service;
  }
}
