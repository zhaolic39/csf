<%@page contentType="text/plain;charset=UTF-8" language="java"%><%@page import="java.io.*"%><%@page import="org.apache.commons.io.*"%><%
response.setHeader("Cache-Control", "no-store");
response.setHeader("Pragma", "no-cache");
response.setDateHeader("Expires", 0);


String log_file_name = request.getParameter("log_file_name"); //日志文件名
String log_file_path = com.epgis.synServer.utils.EasyApplicationContextUtils.getServletContext().getRealPath("/") + com.epgis.synServer.utils.Constants.LOG4J_WEB_FOLDER + "\\" + log_file_name;

String log_str = FileUtils.readFileToString(new File(log_file_path));
out.print(log_str);
%>