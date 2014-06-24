package com.epgis.demo;

import net.csf.controller.ServiceParam;

public class DemoRequest extends ServiceParam{
  private String name;
  private String desp;
  public String getName() {
    return name;
  }
  public void setName(String name) {
    this.name = name;
  }
  public String getDesp() {
    return desp;
  }
  public void setDesp(String desp) {
    this.desp = desp;
  }
  
}
