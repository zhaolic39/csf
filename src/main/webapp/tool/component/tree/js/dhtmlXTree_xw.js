/*
Copyright Scand LLC http://www.scbr.com
To use this component please contact info@scbr.com to obtain license
*/
/*
Purpose: saving state extension for dhtmlxTree
Inner Version: 1.3
Last updated: 05.07.2005
*/

dhtmlXTreeObject.prototype._serEnts=[["&","&amp;"],["<","&lt;"],[">","&gt;"]];

/**
*     @desc: register XML entity for replacement while initialization
*     @type: public
*     @edition: Professional
*	  @param: char - source char
*	  @param: fullXML - target entity
*     @topic: 2
*/
dhtmlXTreeObject.prototype.registerXMLEntity=function(char,entity){
    this._serEnts[this._serEnts.length]=[char,entity,new RegExp(char,"g")];
}

/**
*     @desc: configure XML serialization
*     @type: public
*     @edition: Professional
*	  @param: userData - enable/disable user data serialization
*	  @param: fullXML - enable/disable full XML serialization
*	  @param: escapeEntities - convert tag brackets to related html entitites
*	  @param: userDataAsCData - output user data in CDATA sections
*	  @param: DTD - if specified, then set as XML's DTD
*     @topic: 2
*/
dhtmlXTreeObject.prototype.setSerializationLevel=function(userData,fullXML,escapeEntities,userDataAsCData,DTD){
	this._xuserData=convertStringToBoolean(userData);
	this._xfullXML=convertStringToBoolean(fullXML);
    this._dtd=DTD;
    this._xescapeEntities=convertStringToBoolean(escapeEntities);
    if (convertStringToBoolean(userDataAsCData)){
        this._apreUC="<![CDATA[";
        this._apstUC="]]>";
    }
    else{
    }

    for (var i=0; i< this._serEnts.length; i++)
        this._serEnts[i][2]=new RegExp(this._serEnts[i][0],"g");
}

/**
*     @desc: return xml description of tree
*     @type: public
*     @edition: Professional
*     @topic: 2
*/
dhtmlXTreeObject.prototype.serializeTree=function(){
    if (this.stopEdit) this.stopEdit();
    this._apreUC=this._apreUC||"";
    this._apstUC=this._apstUC||"";
	var out='<?xml version="1.0"?>';
    if (this._dtd)
        out+="<!DOCTYPE tree SYSTEM \""+this._dtd+"\">";
    out+='<tree id="'+this.rootId+'">';

		for (var i=0; i<this.htmlNode.childsCount; i++)
		out+=this._serializeItem(this.htmlNode.childNodes[i]);
	out+="</tree>";
	return out;
};
/**
*     @desc: return xml description of tree item
*     @type: private
*     @param: itemNode - tree item object
*     @edition: Professional
*     @topic: 2
*/
dhtmlXTreeObject.prototype._serializeItem=function(itemNode){


	if (itemNode.unParsed)
		if (document.all)
			return itemNode.unParsed.xml;
		else{
  var xmlSerializer = new XMLSerializer();
  return xmlSerializer.serializeToString(itemNode.unParsed);
		}


	var out="";
	if (this._selected.length)
		var lid=this._selected[0].id;
	else lid="\"";


    var text=itemNode.span.innerHTML;

    if (this._xescapeEntities)
        for (var i=0; i<this._serEnts.length; i++)
            text=text.replace(this._serEnts[i][2],this._serEnts[i][1]);

	if (!this._xfullXML)
		out='<item id="'+itemNode.id+'" '+(this._getOpenState(itemNode)==1?' open="1" ':'')+(lid==itemNode.id?' select="1"':'')+' text="'+text+'"'+( ((this.XMLsource)&&(itemNode.XMLload==0))?" child=\"1\" ":"")+'>';
	else
		out='<item id="'+itemNode.id+'" '+(this._getOpenState(itemNode)==1?' open="1" ':'')+(lid==itemNode.id?' select="1"':'')+' text="'+text+'" im0="'+itemNode.images[0]+'" im1="'+itemNode.images[1]+'" im2="'+itemNode.images[2]+'" '+(itemNode.acolor?('aCol="'+itemNode.acolor+'" '):'')+(itemNode.scolor?('sCol="'+itemNode.scolor+'" '):'')+(itemNode.checkstate==1?'checked="1" ':(itemNode.checkstate==2?'checked="-1"':''))+(itemNode.closeable?'closeable="1" ':'')+'>';

	if ((this._xuserData)&&(itemNode._userdatalist))
		{
		var names=itemNode._userdatalist.split(",");
		for  (var i=0; i<names.length; i++)
			out+="<userdata name=\""+names[i]+"\">"+this._apreUC+itemNode.userData["t_"+names[i]]+this._apstUC+"</userdata>";
		}

		for (var i=0; i<itemNode.childsCount; i++)
			out+=this._serializeItem(itemNode.childNodes[i]);



	out+="</item>";
	return out;
}
/**
*     @desc: save selected item to cookie
*     @type: public
*     @param: name - optional, cookie name
*     @param: cookie_param - additional parametrs added to cookie
*     @edition: Professional
*     @topic: 2
*/
dhtmlXTreeObject.prototype.saveSelectedItem=function(name,cookie_param){
	name=name||"";
	this.setCookie("treeStateSelected"+name,this.getSelectedItemId(),cookie_param);
}
/**     @desc: restore selected item from cookie
*     @type: public
*     @param: name - optional, cookie name
*     @edition: Professional
*     @topic: 2
*/
dhtmlXTreeObject.prototype.restoreSelectedItem=function(name){
	name=name||"";
	var z=this.getCookie("treeStateSelected"+name);
	this.selectItem(z,false);
}


/**   @desc: enable/disable autosaving selected node in cookie
*     @type: public
*     @param: mode - true/false
*     @edition: Professional
*     @topic: 2
*/
dhtmlXTreeObject.prototype.enableAutoSavingSelected=function(mode,cookieName){
 this.assMode=convertStringToBoolean(mode);
 this.assCookieName=cookieName;
}


/**   @desc: save tree to cookie
*     @type: public
*     @param: name - optional, cookie name
*     @param: cookie_param - additional parametrs added to cookie
*     @edition: Professional
*     @topic: 2
*/
dhtmlXTreeObject.prototype.saveState=function(name,cookie_param){
	var z=this._escape(this.serializeTree());
	var kusok = 4000;
	if (z.length>kusok)
	{
		if(navigator.appName.indexOf("Microsoft")!=-1)
			return false;//IE max cookie length is ~4100
	this.setCookie("treeStatex"+name,Math.ceil(z.length/kusok));
		for (var i=0; i<Math.ceil(z.length/kusok); i++)
		{
			this.setCookie("treeStatex"+name+"x"+i,z.substr(i*kusok,kusok),cookie_param);
		}
	}
	else
		this.setCookie("treeStatex"+name,z,cookie_param);
	var z=this.getCookie("treeStatex"+name);
    if (!z) {
		this.setCookie("treeStatex"+name,"",cookie_param);
		return false;
	}
    return true;
}
/**   @desc: load tree from cookie
*     @type: public
*     @param: name - optional,cookie name
*     @edition: Professional
*     @topic: 2
*/
dhtmlXTreeObject.prototype.loadState=function(name){
	var z=this.getCookie("treeStatex"+name);
//    alert("treeStatex"+name);
    if (!z) return false;

	if (z.length)
	{
		if (z.toString().length<4)
		{

			var z2="";
			for (var i=0; i<z; i++){
				z2+=this.getCookie("treeStatex"+name+"x"+i);
                }
			z=z2;
		}
		this.loadXMLString((this.utfesc=="utf8")?decodeURI(z):unescape(z));
	}

    return true;
}
/**   @desc: save cookie
*     @type: private
*     @param: name - cookie name
*     @param: value - cookie value
*     @param: cookie_param - additional parametrs added to cookie
*     @edition: Professional
*     @topic: 0
*/

dhtmlXTreeObject.prototype.setCookie=function(name,value,cookie_param) {
	var str = name + "=" + value +  (cookie_param?("; "+cookie_param):"");
  /*  ((expires) ? "; expires=" + expires.toGMTString() : "") +
    ((path) ? "; path=" + path : "; path=/") +
    ((domain) ? "; domain=" + domain : "") +
    ((secure) ? "; secure" : "");*/
	document.cookie = str;
}

/**   @desc: get cookie
*     @type: private
*     @param: name - cookie name
*     @edition: Professional
*     @topic: 0
*/
dhtmlXTreeObject.prototype.getCookie=function(name) {
	var search = name + "=";
	if (document.cookie.length > 0) {
		var offset = document.cookie.indexOf(search);
		if (offset != -1) {
			offset += search.length;
			var end = document.cookie.indexOf(";", offset);
			if (end == -1)
				end = document.cookie.length;
			return document.cookie.substring(offset, end);
						}		}
};

dhtmlXTreeObject.prototype._createSelfExC=dhtmlXTreeObject.prototype._createSelf;
dhtmlXTreeObject.prototype._createSelf=function(){
	 this.oldOnSelect=this.onRowSelect;
	 this.onRowSelect=function(e,htmlObject,mode){
 		if (!htmlObject) htmlObject=this;
		htmlObject.parentObject.treeNod.oldOnSelect(e,htmlObject,mode);
		if (htmlObject.parentObject.treeNod.assMode)
			htmlObject.parentObject.treeNod.saveSelectedItem(htmlObject.parentObject.treeNod.assCookieName);
		}

		return this._createSelfExC();
}

/**   @desc: save open nodes to cookie
*     @type: public
*     @edition: Professional
*     @param: name - optional,cookie name
*     @param: cookie_param - additional parametrs added to cookie
*     @topic: 2
*/
dhtmlXTreeObject.prototype.saveOpenStates=function(name,cookie_param){
    var z="";
    for (var i=0; i<this.htmlNode.childsCount; i++)
    	z=this._collectOpenStates(this.htmlNode.childNodes[i],z);
    z=z.replace(/\,\,/g,"");

	this.setCookie("treeOpenStatex"+name,z,cookie_param);
};

/**   @desc: restore open nodes from cookie
*     @type: public
*     @edition: Professional
*     @param: name - optional,cookie name
*     @topic: 2
*/
dhtmlXTreeObject.prototype.loadOpenStates=function(name){
	for (var i=0; i<this.htmlNode.childsCount; i++)
    	this._xcloseAll(this.htmlNode.childNodes[i]);
	var z=getCookie("treeOpenStatex"+name);
	if (z) {
		var arr=z.split(this.dlmtr);
		for (var i=0; i<arr.length; i++)
			{
            var zNode=this._globalIdStorageFind(arr[i]);
            if (zNode){
                if  ((!zNode.XMLload)&&(zNode.id!=this.rootId)){
                     this._delayedLoad(zNode,"loadOpenStates('"+name+"')");
                     return;
                    }
                else
           			this.openItem(arr[i]);
                }
			}
	}
};

dhtmlXTreeObject.prototype._delayedLoad=function(node,name){
    this.afterLoadMethod=name;
	this.onLoadReserve = this.onXLE; //save loading end handler
	this.setOnLoadingEnd(this._delayedLoadStep2); //set on XML data loading end handler
    this.loadXML(this.XMLsource+getUrlSymbol(this.XMLsource)+"id="+this._escape(node.id));
}
dhtmlXTreeObject.prototype._delayedLoadStep2=function(tree){
	tree.onXLE=tree.onLoadReserve; //save loading end handler
//    if (tree.onXLE) tree.onXLE(tree);
    window.setTimeout( function() { eval("tree."+tree.afterLoadMethod);  } ,100);
	if (tree.onXLE) tree.onXLE(tree);

}

/**   @desc: build list of opened nodes
*     @type: private
*     @edition: Professional
*     @param: node - start tree item
*     @param: list - start list value
*     @topic: 2
*/
dhtmlXTreeObject.prototype._collectOpenStates=function(node,list){
	if (this._getOpenState(node)==1)
    {
    list+=this.dlmtr+node.id;
	for (var i=0; i<node.childsCount; i++)
		list=this._collectOpenStates(node.childNodes[i],list);
    }
	return list;
};

/**   @desc: save cookie
*     @type: private
*     @edition: Professional
*     @param: name - cookie name
*     @param: value - cookie value
*     @topic: 0
*/
function setCookie(name,value) {
	document.cookie = name+'='+value;
}

/**   @desc: get cookie
*     @type: private
*     @edition: Professional
*     @param: name - cookie name
*     @topic: 0
*/
function getCookie(name) {
	var search = name + "=";
	if (document.cookie.length > 0) {
		var offset = document.cookie.indexOf(search);
		if (offset != -1) {
			offset += search.length;
			var end = document.cookie.indexOf(";", offset);
			if (end == -1)
				end = document.cookie.length;
			return (document.cookie.substring(offset, end));
						}		}
};

/**
*     @desc: expand target node and all child nodes (same as openAllItems, but works in dynamic trees)
*     @type: public
*     @edition: Professional
*     @param: itemId - node id, optional
*     @topic: 4
*/
	dhtmlXTreeObject.prototype.openAllItemsDynamic = function(itemId)
	{
        this.ClosedElem=new Array();
        this.G_node=null;
		var itemNode = this._globalIdStorageFind(itemId||this.rootId); //get node object by id of tree sart node
		this.onLoadReserve = this.onXLE; //save loading end handler
		this.setOnLoadingEnd(this._loadAndOpen); //set on XML data loading end handler
		this._openAllNodeChilds(itemNode, 0); //open closed nodes that have data, or find nodes that have no data yet
		if(this.ClosedElem.length>0) this._loadAndOpen(this); //if there are not loaded items -> run load&open routine
	};

	dhtmlXTreeObject.prototype._openAllNodeChilds = function(itemNode)
	{
			//for dynamic loading
    	if ((itemNode.XMLload==0)||(itemNode.unParsed))  this.ClosedElem.push(itemNode);  //if not loaded put in array
		for (var i=0; i<itemNode.childsCount; i++) //for all childnodes
		{
			//no dynamic loading
			if(this._getOpenState(itemNode.childNodes[i])<0) this._HideShow(itemNode.childNodes[i],2); //if closed -> open
			if(itemNode.childNodes[i].childsCount>0) this._openAllNodeChilds(itemNode.childNodes[i]); //if has childs -> run same routine for that node

			//for dynamic loading
			if ((itemNode.childNodes[i].XMLload==0)||(itemNode.childNodes[i].unParsed)) this.ClosedElem.push(itemNode.childNodes[i]); //if not loaded put in array
		}
	}

	dhtmlXTreeObject.prototype._loadAndOpen = function(that)
	{
		if(that.G_node) //if there was loaded one node
		{
			that._openItem(that.G_node); //open it
			that._openAllNodeChilds(that.G_node); //run open/find closed nodes for childs of this node
			that.G_node = null; //erase "just loaded node" pointer
		}

		if(that.ClosedElem.length>0) that.G_node = that.ClosedElem.shift(); //get not loaded node if any left in array

		if(that.G_node)
            if (that.G_node.unParsed)
                that.reParse(that.G_node);
            else
                window.setTimeout( function(){  that._loadDynXML(that.G_node.id); },100);
        else
			{
            that.onXLE = that.onLoadReserve; //restore loading end handler if finished opening
            if (that.onXLE) that.onXLE(that);
			}
	}


/**
*     @desc: expand list of nodes in dynamic tree (wait of loading of node before expanding next)
*     @type: public
*     @edition: Professional
*     @param: list - list of nodes which will be expanded
*     @param: flag - true/false - select last node in the list
*     @topic: 4
*/
    dhtmlXTreeObject.prototype.openItemsDynamic=function(list,flag){
        this._opnItmsDnmcFlg=convertStringToBoolean(flag);
        this.onLoadReserve = this.onXLE;
        this.setOnLoadingEnd(this._stepOpen);
        this.ClosedElem=list.split(",").reverse();
        this._stepOpen(this);
        }

 dhtmlXTreeObject.prototype._stepOpen=function(that){
 if(that.ClosedElem.length)
{
 that.G_node=that.ClosedElem.pop();
 if(!that.ClosedElem.length){
 that.onXLE = that.onLoadReserve;
 that.selectItem(that.G_node,that._opnItmsDnmcFlg);
}
 var temp=that._globalIdStorageFind(that.G_node);
 if (temp.XMLload)
    that._stepOpen(that);
 else
     that.openItem(that.G_node);
}
}

    dhtmlXTreeObject.prototype._stepOpen=function(that){
        if (that.ClosedElem.length)
            {
            that.G_node=that.ClosedElem.pop();
            if (!that.ClosedElem.length){
                that.onXLE = that.onLoadReserve;
                that.selectItem(that.G_node,that._opnItmsDnmcFlg);
                }
         var temp=that._globalIdStorageFind(that.G_node);
         if (temp.XMLload)
            that._stepOpen(that);
         else
             that.openItem(that.G_node);
            }
        }


