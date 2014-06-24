<%@page contentType="text/html;charset=UTF-8" language="java"%>
<%@ page import="java.io.*" %>
<%@ page import="com.epgis.csf.utils.*" %>
<%@ page import="java.util.Map.Entry"  %>
<%@ page import="java.util.*" %>
<%
response.setHeader("Cache-Control", "no-store");
response.setHeader("Pragma", "no-cache");
response.setDateHeader("Expires", 0);

String param_temp = "<content>\n</content>";
%>
<html>
<head>
<meta http-equiv="Content-type" content="text/html;charset=utf-8">
 <jsp:include flush="true" page="common_inc.jsp"></jsp:include>
    <script type="text/javascript">
      function cmdSubmit(){
        var http_request = "";
        if($("#format").val() == "XML"){
          http_request  = "<?xml version=\"1.0\" encoding=\"UTF-8\" standalone=\"yes\"?>" +
	            "<request>" + 
	            "<httpCommand>"+$('#service_commad').val()+"</httpCommand>" + 
	            "<httpCommandBody>"+$('#service_param').val()+"</httpCommandBody>" +
	            "<requestDate><%=DateFormatUtils.formatDefaultDate(new Date())%></requestDate><verifyCode></verifyCode>" +
	            "</request>";
        }
        else{
          http_request = "{\"httpCommand\":\""+$('#service_commad').val()+
            "\", \"requestDate\":\""+<%=DateFormatUtils.formatDefaultDate(new Date())%>+
            "\", \"verifyCode\":\"\", \"httpCommandBody\":"+$('#service_param').val()+"}";
        }
        $('#request').val(http_request);
        service_form.submit();
      }
      
    </script>
</head>
<body>
	<table height="100%" width="100%" >
	  <tr height=100%> 
	  <td style="height:0px;margin-top: 0px;" valign=top >
	  <form id="service_form" method="post" target="service_frame" action="<%=request.getContextPath()%>/api/httpCommand.jsp">
	  <table width="100%"  class="table-form">
	  <tr>
      <td class="td_label">HTTP命令:</td>
      <td class="td_input">
        <input type="text" id="service_commad" style="width:100%">
        <input name="isFormat" type="hidden" value="true">
      </td>
    </tr>
    <tr>
      <td class="td_label">报文格式:</td>
      <td class="td_input">
        <select name="format" id="format">
          <option value="XML">XML</option>
          <option value="JSON">JSON</option>
        </select>
      </td>
    </tr>
	  <tr>
	    <td class="td_label">参数:</td>
	    <td class="td_input">
	      <textarea id="service_param" style="width:100%;height:200px"><%=param_temp%></textarea>
	      <input type="hidden" name="request" id="request" >
	    </td>
	  </tr>
	  <tr>
	    <td align=center colspan=4>
	      <input type="button" value="提交" onclick="cmdSubmit()">
	    </td>
	  </tr>
	  </table>
	  </form>
	  </td>
	  </tr>
	  <tr>
	    <td>
	      <iframe name="service_frame" width="100%" height="100%" src="about:blank"></iframe>
	    </td>
	  </tr>
	</table>
</body>
</html>