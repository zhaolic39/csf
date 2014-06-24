<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@ page import="java.text.*" %>
<%@ page import="java.io.*" %>
<%@ page import="com.epgis.demo1.*" %>
<%
String path = request.getContextPath(); 
%>
<html>
<head>
	<meta http-equiv="Content-type" content="text/html;charset=utf-8">
	<script type="text/javascript" src="jquery-1.8.0.min.js"></script>
	<script type="text/javascript" src="jquery.json-2.4.min.js"></script>
	<script type="text/javascript" src="jquery.csf.js"></script>
	<script type="text/javascript">
  var AppConfig = {
      WEB_CONTEXT_PATH : "<%=path%>"
  };
  
	function init(){
	  var a = {demo_id:111222, demo_desc:"desc"};
	  var callBack = function(content){
	    alert(content.respBody.demoInfo.demo_id);
	  };
	  $.CsfAgent.invoke("DEMO.SAVEDEMO", a, callBack);
	}
	</script>
</head>
<body onload="init()"></body>
</html>