package com.epgis.csf.request;

import java.util.Map;

/**
 * <p>Title: CSF服务请求参数 </p>
 * <p>Description:</p>
 *
 * @author zhaoli
 * @version 1.0 2013-10-24
 */
public class CSFRequest{
  /** 服务命令 */
  private String command;
  
  /** 命令参数 */
  private Map<String, Object> body;
  
  public CSFRequest(String command, Map<String, Object> body){
    this.command = command;
    this.body = body;
  }

  public CSFRequest() {
  }

  public String getCommand() {
    return command;
  }

  public void setCommand(String command) {
    this.command = command;
  }

  public Map<String, Object> getBody() {
    return body;
  }

  public void setBody(Map<String, Object> body) {
    this.body = body;
  }

}
