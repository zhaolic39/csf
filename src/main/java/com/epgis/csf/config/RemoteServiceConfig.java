package com.epgis.csf.config;

/**
 * <p>Title: 远程服务配置 </p>
 * <p>Description:</p>
 *
 * @author zhaoli
 * @version 1.0 2013-10-24
 */
public class RemoteServiceConfig {
  private String remote_url;
  
  private String command;
  

  private String remote_command;
  
  private String desc;
  
  public RemoteServiceConfig(String url, String c, String remote_command, String d){
    remote_url = url;
    command = c;
    desc = d;
    this.remote_command = remote_command;
  }
  
  public String getRemote_command() {
    return remote_command;
  }
  
  public void setRemote_command(String remote_command) {
    this.remote_command = remote_command;
  }

  public String getRemote_url() {
    return remote_url;
  }

  public void setRemote_url(String remote_url) {
    this.remote_url = remote_url;
  }

  public String getCommand() {
    return command;
  }

  public void setCommand(String command) {
    this.command = command;
  }

  public String getDesc() {
    return desc;
  }

  public void setDesc(String desc) {
    this.desc = desc;
  }
}
