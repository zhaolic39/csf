package com.epgis.demo;

import java.util.List;

public class DemoResponse {
  private String id;
  private String desp;
  private List<User> list;
  
  public List<User> getList() {
    return list;
  }
  public void setList(List<User> list) {
    this.list = list;
  }
  public String getId() {
    return id;
  }
  public void setId(String id) {
    this.id = id;
  }
  public String getDesp() {
    return desp;
  }
  public void setDesp(String desp) {
    this.desp = desp;
  }
  
  
}
