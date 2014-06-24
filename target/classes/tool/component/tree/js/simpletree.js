//auther: he xu hua
//auther: wen jian guo

function simpletreeInit(tree, dataUrl,smartLoadUrl,afterCall,onLoadingEnd,defaultOnClick,defaultOnCheck)
{
    if (tree.smartLoad)
    {		 
//        tree.setXMLAutoLoadingBehaviour("nodeId");
//        tree.setChildCalcMode("leafs");
        tree.setXMLAutoLoading(CompUtil.getFirstStrNotEmpty(smartLoadUrl,dataUrl));
        tree.enableSmartXMLParsing(true);
    }

    tree.loadXML(dataUrl,afterCall);
    function treeOnClick(id)
    {
        var js = tree.getUserData(id, "onclickJs");
        if(js!=null && js.indexOf('(')==-1)js+="(id)"
        eval(js);
    }

    //没有默认的单击事件的时候，才去显示的加载单击事件。 add by wenjg
    if (!defaultOnClick) {
         tree.setOnClickHandler(treeOnClick);
    }

    function treeOncheckJs(id,state)
    {
        var js = tree.getUserData(id, "oncheckJs");
        if(js!=null && js.indexOf('(')==-1)js+="(id,state)"
        eval(js);
    }
   //没有默认的选中事件的时候，才去显示的加载选中事件。 add by wenjg
    if (!defaultOnCheck) {
        tree.setOnCheckHandler(treeOncheckJs);
    }

    //add by wenjg 2009-01-07
    //增加右键点击事件 begin
    function treeOnrightclickJs(id) {
    	var js = tree.getUserData(id, "onrightclickJs");
    	if(js!=null && js.indexOf('(')==-1)js+="(id)"
        eval(js);
    }
    tree.setOnRightClickHandler(treeOnrightclickJs);
    //end
    
    function treeOnLoadStart(tree, id)
    {
        var d = new Date();
        var newId = "id_" + d.valueOf();
        tree.insertNewChild(id, newId, "正在加载...", "", "loading.gif", "", "", 'SELECT');
        tree.showItemCheckbox(newId, false);
        tree.setUserData(id, "load_id", newId);
    }
    tree.setOnLoadingStart(treeOnLoadStart);

    function treeOnLoadEnd(tree, id)
    {
        var loadId = tree.getUserData(id, "load_id");
        if (loadId)
            tree.deleteItem(loadId, false);
        
    }
    tree.setOnLoadingEnd(onLoadingEnd||treeOnLoadEnd);
}

//util
String.prototype.trim = function()
{
    return this.replace(/(^\s*)|(\s*$)/g, "");
}

String.prototype.ltrim = function()
{
    return this.replace(/(^\s*)/g, "");
}

String.prototype.rtrim = function()
{
    return this.replace(/(\s*$)/g, "");
}

function notEmpty(inputString)
{
    if (!inputString) return false;
    var str = inputString.replace(/(^\s*)|(\s*$)/g, "");
    if (str == "") return false;
    return true;
}

document.createElement4IE = function(html, container, nextNode)
{
    if (!container)container = document.body;
    if (!container)return;
    var o = document.createElement(html);
    if (nextNode)
        container.insertBefore(o, nextNode);
    else
        container.appendChild(o);
    return o;
}


//advance
var simpletree_http_request = false;
function makeTreeRequest(url, func)
{
    simpletree_http_request = false;

    if (window.ActiveXObject)
    { // IE
        try
        {
            simpletree_http_request = new ActiveXObject("Msxml2.XMLHTTP");
        } catch (e)
        {
            try
            {
                simpletree_http_request = new ActiveXObject("Microsoft.XMLHTTP");
            } catch (e)
            {
            }
        }
    } else  if (window.XMLHttpRequest)
    { // Mozilla, Safari,...
        simpletree_http_request = new XMLHttpRequest();
        if (simpletree_http_request.overrideMimeType)
        {
            simpletree_http_request.overrideMimeType('text/xml');
        }
    }

    if (!simpletree_http_request)
    {
        alert('Giving up :( Cannot create an XMLHTTP instance');
        return false;
    }
    simpletree_http_request.onreadystatechange = func;
    simpletree_http_request.open('post', url, true);
    simpletree_http_request.send(null);

}
//item change function


function setOnclick(tree, clickStr, treeId)
{
    if (!treeId)
        var treeId = tree.getSelectedItemId();
    tree.setUserData(treeId, "onclickJs", clickStr);
}
function setChecked(tree, flag, treeId)
{
    if (!treeId)
        var treeId = tree.getSelectedItemId();
    tree.setUserData(treeId, "hasCheckbox", flag);
}

//add by wenjg
function setOnrightclick(tree, clickStr, treeId)
{
    if (!treeId)
        var treeId = tree.getSelectedItemId();
    tree.setUserData(treeId, "onrightclickJs", clickStr);
}


function searchItem(tree, searchStr, flag)
{
    var resultId = "";
    if (!searchStr)
    {
        alert();
        return;
    }
    if (typeof(searchStr) != "string")
    {
        alert("非字符串");
        return;
    }
    if (!flag || flag != 1)
    {
        closeAllItems(tree);
        tree.searchIds = "";
    }

    var idStrs = tree.getAllSubItems(tree.rootId).split(',');
    var searchIds = tree.searchIds.split(',');
    for (var i = 0; i < idStrs.length; i++)
    {
        if (tree.getItemText(idStrs[i]).indexOf(searchStr) != -1)
        {
            var sFlag = false;
            for (var j = 0; j < searchIds.length; j++)
            {
                if (idStrs[i] == searchIds[j])
                {
                    sFlag = true;
                    break;
                }
            }
            if (sFlag)
                continue;
            tree.selectItem(idStrs[i], false);
			//tree.setItemColor(idStrs[i],'black','yellow');
            tree.searchIds += "," + idStrs[i];
            resultId = idStrs[i];
            break;
        }
    }
    if (resultId == "")
        alert();
    return resultId;
}
function findItem(tree, searchString, direction, top)
{
    return tree.findItem(searchString, direction, top);
}
function getSimpletreeXml(tree)
{
    tree.setSerializationLevel(1, 1, 1, 1, 0);
    var xmlStr = tree.serializeTree();
    return xmlStr;
}

function setIconSize(tree, iconX, iconY)
{
    tree.setIconSize(iconX, iconY, tree.getSelectedItemId());
}

function setItemStyle(tree, styleStr)
{
    tree.setItemStyle(tree.getSelectedItemId(), styleStr);
}
function saveTreeToDB(frm, tree, flag)
{
    if (flag == 1)
    {
        tree.closeAllItems();
        tree.clearSelection();
    }
    var xmlString = getSimpletreeXml(tree);
    var i = document.createElement4IE("<input name='xmlString' type='hidden'>", frm);
    i.value = xmlString;
    //jslog.info(xmlString);
    //frm.submit();
}
function ChangeMenu(itemId)
{

}
function onButtonClick(menuitemId, itemId, context, tree)
{
    if (menuitemId == "menu_open") tree.openItem(itemId);
    else if (menuitemId == "menu_close") tree.closeItem(itemId);
    else if (menuitemId == "menu_open_all") tree.openAllItems(itemId);
    else if (menuitemId == "menu_close_all") tree.closeAllItems(itemId);
    else if (menuitemId == "branch_add") addBranch(tree, itemId);
    else if (menuitemId == "branch_edit") editBranch(context.innerHTML, tree, itemId);
    else if (menuitemId == "branch_remove")
    {
        if (window.confirm("确定要删除（" + context.innerHTML + "）吗？")) tree.deleteItem(itemId, false);
    }
    else if (menuitemId == "checkbox_add")
    {
        tree.showItemCheckbox(itemId, true);
        setChecked(tree, 1, itemId);
    }
    else if (menuitemId == "checkbox_remove")
    {
        tree.showItemCheckbox(itemId, false);
        setChecked(tree, 0, itemId);
    }
    else if (menuitemId == "help_index") alert("后期提供！");
    else if (menuitemId == "help_license") alert("");

}
function addBranch(tree, itemId)
{
    var value = window.showModalDialog(treeBasePath + "../addItem.htm", window, "dialogHeight:400px;dialogWidth:600px;");
    if (!value)
        return;
    var d = new Date();
    var newId = "id_" + d.valueOf();
    if ("brother" == value.branch_type)
        tree.insertNewNext(itemId, newId, value.branch_name, "", value.im0, value.im1, value.im2, 'SELECT');
    else
        tree.insertNewChild(itemId, newId, value.branch_name, "", value.im0, value.im1, value.im2, 'SELECT');
    tree.showItemCheckbox(newId, false);
    if (value.onclick_event != "");
    setOnclick(tree, value.onclick_event, newId);
}
function editBranch(inner, tree, itemId)
{
    var value = window.showModalDialog(treeBasePath + "../editItem.htm", inner, "dialogHeight:600px;dialogWidth:600px;");
    if (!value)
        return;
    if (value.branch_name != "")
        tree.setItemText(itemId, value.branch_name, "");
    if (value.im0 != "" && value.im1 != "" && value.im2 != "")
        tree.setItemImage2(itemId, value.im0, value.im1, value.im2);
    if (value.color_default != "" && value.color_selected != "")
        tree.setItemColor(itemId, value.color_default, value.color_selected);

    if (value.onclick_event != "");
    setOnclick(tree, value.onclick_event, itemId);
}