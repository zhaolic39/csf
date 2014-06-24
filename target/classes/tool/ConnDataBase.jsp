<%@ page language="java" import="java.util.*"   pageEncoding="utf-8"%>
<%@ page import="java.sql.*" %>

<%
	String driverClass = request.getParameter("driverclass");
	String url = request.getParameter("url");
	String user = request.getParameter("user");
	String password = request.getParameter("password");
	String type = request.getParameter("type");
	try{
		Class.forName(driverClass);
		Connection con = DriverManager.getConnection(url, user, password);
		if(type.equals("s"))
			out.print("源数据库连接成功！");
		else
			if(type.equals("d"))
				out.print("目标数据库连接成功！");
	}catch(Exception e){
		out.print("数据库连接异常："+e);
	}
%>