<%
/**
 * 处理flex发来的日志打印请求
**/
%>
<%@page contentType="text/html;charset=UTF-8" language="java"%>
<%@page import="java.util.*"%>
<%@page import="java.net.*"%>
<%@page import="org.apache.log4j.*"%>
<%@page import="com.epgis.synServer.utils.*"%>
<%@page import="org.apache.commons.lang.StringUtils"%>
<%@page isELIgnored="false"%>
<%
request.setCharacterEncoding(Constants.DEFAULT_CHARSET);
String log_id = request.getParameter("log_id"); //日志ID，根据日志ID输出到相应的LOG文件中
String log_str = request.getParameter("log_str"); //日志内容

Logger log = Log4jUtils.getInstance().getLoggerByLogId(log_id);
log.debug(log_str);
%>