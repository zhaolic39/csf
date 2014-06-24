<%@ page language="java" contentType="text/html;charset=utf-8" import="java.util.*"%>
<%@ page import="java.io.*" %>
<%@ page import="com.epgis.csf.controller.*" %>
<%@ page import="com.epgis.csf.utils.*" %>
<%@ page import="com.epgis.csf.service.*" %>

<%@ page import="java.util.Map.Entry"  %>
<%@ page import="java.util.*" %>
<%
ServiceManager http_manager = (ServiceManager)EasyApplicationContextUtils.getBeanByType(ServiceManager.class);
Map<String, IService> http_map = http_manager.getHttpServices();
List<Map.Entry<String, IService>> service_list = new ArrayList<Map.Entry<String, IService>>(http_map.entrySet()){};
Collections.sort(service_list, new Comparator<Map.Entry<String, IService>>(){
  public int compare(Map.Entry<String, IService> mapping1, Map.Entry<String, IService> mapping2){
    return mapping1.getKey().compareTo(mapping2.getKey());
  }
});

StringBuffer local = new StringBuffer();
StringBuffer remote = new StringBuffer();
for(Entry<String, IService> entry:service_list){
  IService s = entry.getValue();
  if(s instanceof LocalService){
    local.append("<tr>");
    local.append("<td>"+entry.getKey() + "</td>");
    local.append("<td>"+s.getImplementDesc() + "</td>");
    local.append("<td>"+s.getServiceDesc() + "</td>");
    local.append("</tr>");
  }
  else if(s instanceof RemoteService){
    remote.append("<tr>");
    remote.append("<td>"+entry.getKey() + "</td>");
    remote.append("<td>"+s.getImplementDesc() + "</td>");
    remote.append("<td>"+s.getServiceDesc() + "</td>");
    remote.append("</tr>");
  }
}
%>
<head>
<meta http-equiv="Content-type" content="text/html;charset=utf-8">
<meta http-equiv="X-UA-Compatible" content="IE=edge">
 <jsp:include flush="true" page="common_inc.jsp"></jsp:include>
    <script type="text/javascript">
      function refreshService(){
        service_refresh_frame.location.replace('service_refresh.jsp');
      }
    
    </script>
</head>
<body style="margin: 0px;overflow: auto;" >
  <table height="100%" width="100%" class="table ">
    <tr height=100%> 
    <td style="height:0px;margin-top: 0px;" valign=top >
    <table width="100%" class="table ">
    <tr class="seperator-title">
      <td>注册服务</td>
    </tr>
    <tr>
      <td>Local服务:</td>
    </tr>
    <tr>
      <td>
        <table width="100%" class="table table-hover table-condensed">
          <thead>
          <tr>
            <th>httpCommand</th>
            <th>实现描述</th>
            <th>服务描述</th>
          </tr>
          </thead>
        <%=local.toString()%>
        </table>
      </td>
    </tr>
    <tr>
      <td>Remote服务:</td>
    </tr>
    <tr>
      <td>
        <table width="100%" class="table table-hover table-condensed">
          <thead>
          <tr>
            <th>httpCommand</th>
            <th>实现描述</th>
            <th>服务描述</th>
          </tr>
          </thead>
        <%=remote.toString()%>
        </table>
      </td>
    </tr>
    </table>
    </td>
    </tr>
    <tr>
      <td>
        <iframe name="service_refresh_frame" width=0 height=0 src="about:blank"></iframe>
      </td>
    </tr>
  </table>
</body>
