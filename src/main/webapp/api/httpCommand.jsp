<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%><%@ page import="java.io.*" %><%@ page import="net.csf.*" 
%><%@ page import="net.csf.request.*" %><%@ page import="net.csf.response.*" %><%@ page import="net.csf.utils.*" 
%><%@ page import="net.csf.exception.BusinessException" %><%@ page import="org.apache.commons.lang.StringUtils" 
%><%@ page import="org.apache.commons.fileupload.*" %><%@ page import="org.apache.commons.fileupload.servlet.*" 
%><%@ page import="org.apache.commons.logging.*" %><%@ page import="net.csf.controller.*,net.csf.conventor.*" %><%!
private static Log logger = LogFactory.getLog(ServiceServlet.class);
%><%
String requestStr = "";
String format = "";  //报文格式
boolean isFormat = "true".equals(request.getParameter("isFormat"));
RequestMessage reqMessage = null;
ResponseMessage responseMsg = null;
FileItem fileItem = null;
try {
  if(ServletFileUpload.isMultipartContent(request)){ //带附件请求
    MultiFormData formdata = new MultiFormData(request);
    requestStr = formdata.getParameter(Constants.REQUEST);
    format = formdata.getParameter(Constants.FORMAT);
    fileItem = formdata.getUploadFile(Constants.FILE_PART);  //附件
  }else{ //普通请求
    requestStr = request.getParameter(Constants.REQUEST);
    format = request.getParameter(Constants.FORMAT);
  }
  if (logger.isDebugEnabled()) {
    logger.debug("requestStr:\n" + formatMessage(format, requestStr));
  }
  if (!StringUtils.isEmpty(requestStr)) {
    reqMessage = MessageUtils.toRequestMessage(requestStr, format);
    reqMessage.setFileItem(fileItem);
    
    responseMsg = ServiceUtils.callService(reqMessage, format);
  } else {
    responseMsg = MessageUtils.createFailResponse("-1", "no request info", "no request info");
  }
} 
catch (BusinessException e) {
  responseMsg = MessageUtils.createFailResponse(e.getErrorCode(), e.getErrorDesc(), e.getErrorResult());
} 
catch (Exception e) {
  logger.error("http command error", e);
  responseMsg = MessageUtils.createFailResponse("1", e.toString(), e.toString());
}

String resp = MessageUtils.toResponseMessage(responseMsg, format);
if(isFormat){
  resp = formatMessage(format, resp);
}
if(logger.isDebugEnabled()){
  logger.debug("responseMsg:\n" + formatMessage(format, resp));
}
response.setCharacterEncoding("UTF-8");
response.setContentType("text/plain; charset=UTF-8");
out.print(resp);
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