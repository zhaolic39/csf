<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@ page import="java.text.*" %>
<%@ page import="java.io.*" %>
<%@ page import="net.csf.utils.*" %>
<%@ page import="net.csf.request.*" %>
<%@ page import="net.csf.response.*" %>
<%
Map<String, Object> param = new HashMap<String, Object>();
param.put("dispatcher_name", "dltest");
param.put("password", "666666");
param.put("org_id", "b8f4ea8a91b44dc0b3c50bc11763614c");
param.put("group_name", "测试用");
CSFRequest req = new CSFRequest("TERMINAL.HTTPCMD_TERMINAL_GROUP_ADD", param);
CSFResponse<Map<String, Object>> res = ServiceUtils.callCSFService(req);

out.print(res.getRespCode() + "," + res.getRespResult());
%>