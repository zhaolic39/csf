<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%><%@ page import="java.io.*" %><%@ page import="net.csf.*" 
%><%@ page import="net.csf.request.*" %><%@ page import="net.csf.conventor.*" %><%@ page import="net.csf.utils.*" 
%><%@ page import="net.csf.exception.BusinessException" %><%@ page import="org.apache.commons.lang.StringUtils" 
%><%@ page import="org.apache.commons.logging.*" %><%@ page import="net.csf.controller.*" 
%><%@ page import="net.csf.service.*" %><%@ page import="java.util.Map.Entry"  %><%!
private static Log logger = LogFactory.getLog(ServiceServlet.class);
%><%
ServiceManager http_manager = (ServiceManager)EasyApplicationContextUtils.getBeanByType(ServiceManager.class);
Map<String, IService> http_map = http_manager.getHttpServices();
Set<Entry<String, IService>> http_command_set = http_map.entrySet();

List<Map<String,Object>> local_list = new ArrayList<Map<String,Object>>();
for(Entry<String, IService> entry:http_command_set){
  IService s = entry.getValue();
  if(s instanceof LocalService){
    Map<String,Object> service = new HashMap<String, Object>();
    service.put("command", s.getCommandName());
    service.put("desc", s.getServiceDesc());
    
    local_list.add(service);
  }
}

String resp = JsonMessageConventor.getInstacne().marshaller(local_list.getClass(), local_list);
response.setCharacterEncoding("UTF-8");
response.setContentType("text/plain; charset=UTF-8");
out.print(resp);
%>