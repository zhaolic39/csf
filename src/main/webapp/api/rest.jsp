<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%><%@ page import="java.io.*" %><%@ page import="net.csf.*" 
%><%@ page import="net.csf.request.*" %><%@ page import="net.csf.response.*" %><%@ page import="net.csf.utils.*" 
%><%@ page import="net.csf.exception.BusinessException" %><%@ page import="org.apache.commons.lang.StringUtils" 
%><%@ page import="org.apache.commons.fileupload.*" %><%@ page import="org.apache.commons.fileupload.servlet.*" 
%><%@ page import="org.apache.commons.logging.*" %><%@ page import="net.csf.controller.*,net.csf.conventor.*" %><%!
private static Log logger = LogFactory.getLog(ServiceServlet.class);
%><%

ServiceManager cmdManger = (ServiceManager) EasyApplicationContextUtils.getBeanByType(ServiceManager.class);
cmdManger.handleRequest(request);
response.setCharacterEncoding("UTF-8");
response.setContentType("text/plain; charset=UTF-8");
out.print("");

response.addCookie()
%><%!
public String formatMessage(String format, String message){
  String s = "";
  try{
	  if(Constants.XML.equals(format)){
	    s = XmlFormatUtils.formatXmlStr(message);
	  }
	  else{
	    Map<String, Object> map = new HashMap<String, Object>();
	    Object respObj = JsonMessageConventor.getInstacne().unmarshaller(map.getClass(), message);
	    map = map.getClass().cast(respObj);
	    s = JsonMessageConventor.getInstacne().getObjectMapper().defaultPrettyPrintingWriter().writeValueAsString(map);
	  }
  }catch(Exception e){
  }
  return s;
}
%>