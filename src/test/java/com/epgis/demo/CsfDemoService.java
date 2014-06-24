package com.epgis.demo;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Service;

import com.epgis.csf.annotation.ServiceMethod;
import com.epgis.csf.controller.JsonRequestParam;

@Service
public class CsfDemoService {
  
  @ServiceMethod(command = "CSF.DEMO.TEST", desc="在线人员名单")
  public DemoResponse test(DemoRequest request){
    List<User> list = new ArrayList<User>();
    User u = new User();
    u.setUsername("username");
    u.setUsersex("male");
    list.add(u);
    
    u = new User();
    u.setUsername("username1");
    u.setUsersex("male1");
    list.add(u);
    
    DemoResponse res = new DemoResponse();
    res.setId(request.getName());
    res.setDesp(request.getDesp());
    res.setList(list);
    return res;
  }
  
  @ServiceMethod(command = "CSF.DEMO.JSON", desc="JSON服务测试")
  public Map<String, Object> test1(JsonRequestParam request){
    String id = request.getString("id");
    String name = request.getString("name");
    Map<String, Object> m = new HashMap<String, Object>();
    m.put("id", id);
    m.put("name", name);
    return m;
  }
}
