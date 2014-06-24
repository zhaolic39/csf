<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%
String path = request.getContextPath();
%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
    <title>工具页面</title>
    <jsp:include flush="true" page="common_inc.jsp"></jsp:include>
    <script type="text/javascript">
      var tabbar = null;
      var tree = null;
      function init(){
        tree = new dhtmlXTreeObject("div_treeframe","100%","100%","0");  //加载树
        tree.setImagePath(treeDefaultImgPath);
        tree.setOnClickHandler(openTab);  //点击叶子事件
        tree.enableTreeLines(true);
        tree.preventIECashing('enable');
        tree.loadXML("tools.xml");

        
        tabbar = new dhtmlXTabBar("div_tabframe", "top", "20");
        tabbar.setImagePath(tabDefaultImgPath);
        tabbar.setStyle('modern');  //winScarf,winBiScarf,winRound
        tabbar.setMargin(-3);
        tabbar.setAlign('left');//left,right
        tabbar.setOffset(5);
        tabbar.setHrefMode('iframes');
        tabbar.enableTabCloseButton(true);
        tabbar.enableAutoReSize(true);
        //tabbar.enableAutoSize(true);
        tabbar.enableScroll(true);
        tabbar.enableContentZone(true);
        tabbar.enableForceHiding(false);
        tabbar.preventIECashing(true);
        
        /*
        tabbar.addTab("task_info", "查看任务详情");
        tabbar.activedTab('task_info','detail.jsp');
        tabbar.addTab("log4j", "日志查看");
        tabbar.activedTab('log4j','log4j.jsp');
        
        tabbar.setTabActive('task_info');
        */
      }
      
      function openTab(tree_id){
        //alert(tree_id);
        //var itemId = tree.getSelectedItemId();
        
        /*获得树节点*/
    //   var text = tree.getItemText(tree_id);
    if(tree_id != "zExtractor"){
    	var tab = tabbar.getTabById(tree_id);
    	if(tab==null){
   			//var itemId = tree.getSelectedItemId();
   			//alert(tree_id);
   			if(!tree.getUserData(tree_id,"url")) return;
        tabbar.addTab(tree_id, tree.getItemText(tree_id));
  	    tabbar.activedTab(tree_id, tree.getUserData(tree_id,"url"));
  	    tabbar.setTabActive(tree_id);  //转到某个tab
    	}else{
    		tabbar.setTabActive(tree_id);  //转到某个tab
    	}
    }
}
      
    </script>
  </head>
<body onload="init()" style="margin: 0px;overflow: auto;" >
  <table height="100%" width="100%">
    <tr> 
      <td width="15%">
        <div id="div_treeframe"></div>
      </td>
      <td width="85%">
        <div id="div_tabframe" style="width:100%;heigth:100%"></div>
      </td>
    </tr>
  </table>
</body>
</html>
