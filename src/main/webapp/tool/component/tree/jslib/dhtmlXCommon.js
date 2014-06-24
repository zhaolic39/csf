function dtmlXMLLoaderObject(b,d,c,a){this.xmlDoc="";if(arguments.length==2){this.async=true}else{this.async=c}this.onloadAction=b||null;this.mainObject=d||null;this.waitCall=null;this.rSeed=a||false;return this}dtmlXMLLoaderObject.prototype.waitLoadFunction=function(a){this.check=function(){if(a.onloadAction!=null){if((!a.xmlDoc.readyState)||(a.xmlDoc.readyState==4)){a.onloadAction(a.mainObject,null,null,null,a);if(a.waitCall){a.waitCall();a.waitCall=null}a=null}}};return this.check};dtmlXMLLoaderObject.prototype.getXMLTopNode=function(b){if(this.xmlDoc.responseXML){var a=this.xmlDoc.responseXML.getElementsByTagName(b);var d=a[0]}else{var d=this.xmlDoc.documentElement}if(d){return d}if((_isIE)&&(!this._retry)){var c=this.xmlDoc.responseText;this._retry=true;this.xmlDoc=new ActiveXObject("Microsoft.XMLDOM");this.xmlDoc.async=false;this.xmlDoc.loadXML(c);return this.getXMLTopNode(b)}dhtmlxError.throwError("LoadXML","Incorrect XML",[this.xmlDoc]);return document.createElement("DIV")};dtmlXMLLoaderObject.prototype.loadXMLString=function(b){try{var c=new DOMParser();this.xmlDoc=c.parseFromString(b,"text/xml")}catch(a){this.xmlDoc=new ActiveXObject("Microsoft.XMLDOM");this.xmlDoc.async=this.async;this.xmlDoc.loadXML(b)}this.onloadAction(this.mainObject);if(this.waitCall){this.waitCall();this.waitCall=null}};dtmlXMLLoaderObject.prototype.loadXML=function(c,b,a){if(this.rSeed){c+=((c.indexOf("?")!=-1)?"&":"?")+"a_dhx_rSeed="+(new Date()).valueOf()}this.filePath=c;try{this.xmlDoc=new XMLHttpRequest();this.xmlDoc.open(b?"POST":"GET",c,this.async);if(b){this.xmlDoc.setRequestHeader("Content-type","application/x-www-form-urlencoded")}this.xmlDoc.onreadystatechange=new this.waitLoadFunction(this);this.xmlDoc.send(null||a)}catch(d){if(document.implementation&&document.implementation.createDocument){this.xmlDoc=document.implementation.createDocument("","",null);this.xmlDoc.onload=new this.waitLoadFunction(this);this.xmlDoc.load(c)}else{this.xmlDoc=new ActiveXObject("Microsoft.XMLHTTP");this.xmlDoc.open(b?"POST":"GET",c,this.async);if(b){this.xmlDoc.setRequestHeader("Content-type","application/x-www-form-urlencoded")}this.xmlDoc.onreadystatechange=new this.waitLoadFunction(this);this.xmlDoc.send(null||a)}}};dtmlXMLLoaderObject.prototype.destructor=function(){this.onloadAction=null;this.mainObject=null;this.xmlDoc=null;return null};function callerFunction(a,b){this.handler=function(c){if(!c){c=event}a(c,b);return true};return this.handler}function getAbsoluteLeft(b){var c=b.offsetLeft;var a=b.offsetParent;while(a!=null){c+=a.offsetLeft;a=a.offsetParent}return c}function getAbsoluteTop(c){var b=c.offsetTop;var a=c.offsetParent;while(a!=null){b+=a.offsetTop;a=a.offsetParent}return b}function convertStringToBoolean(a){if(typeof(a)=="string"){a=a.toLowerCase()}switch(a){case"1":case"true":case"yes":case"y":case 1:case true:return true;break;default:return false}}function getUrlSymbol(a){if(a.indexOf("?")!=-1){return"&"}else{return"?"}}function dhtmlDragAndDropObject(){this.lastLanding=0;this.dragNode=0;this.dragStartNode=0;this.dragStartObject=0;this.tempDOMU=null;this.tempDOMM=null;this.waitDrag=0;if(window.dhtmlDragAndDrop){return window.dhtmlDragAndDrop}window.dhtmlDragAndDrop=this;return this}dhtmlDragAndDropObject.prototype.removeDraggableItem=function(a){a.onmousedown=null;a.dragStarter=null;a.dragLanding=null};dhtmlDragAndDropObject.prototype.addDraggableItem=function(a,b){a.onmousedown=this.preCreateDragCopy;a.dragStarter=b;this.addDragLanding(a,b)};dhtmlDragAndDropObject.prototype.addDragLanding=function(a,b){a.dragLanding=b};dhtmlDragAndDropObject.prototype.preCreateDragCopy=function(a){if(window.dhtmlDragAndDrop.waitDrag){window.dhtmlDragAndDrop.waitDrag=0;document.body.onmouseup=window.dhtmlDragAndDrop.tempDOMU;document.body.onmousemove=window.dhtmlDragAndDrop.tempDOMM;return false}window.dhtmlDragAndDrop.waitDrag=1;window.dhtmlDragAndDrop.tempDOMU=document.body.onmouseup;window.dhtmlDragAndDrop.tempDOMM=document.body.onmousemove;window.dhtmlDragAndDrop.dragStartNode=this;window.dhtmlDragAndDrop.dragStartObject=this.dragStarter;document.body.onmouseup=window.dhtmlDragAndDrop.preCreateDragCopy;document.body.onmousemove=window.dhtmlDragAndDrop.callDrag;if((a)&&(a.preventDefault)){a.preventDefault();return false}return false};dhtmlDragAndDropObject.prototype.callDrag=function(c){if(!c){c=window.event}dragger=window.dhtmlDragAndDrop;if((c.button==0)&&(_isIE)){return dragger.stopDrag()}if(!dragger.dragNode){dragger.dragNode=dragger.dragStartObject._createDragNode(dragger.dragStartNode,c);if(!dragger.dragNode){return dragger.stopDrag()}dragger.gldragNode=dragger.dragNode;document.body.appendChild(dragger.dragNode);document.body.onmouseup=dragger.stopDrag;dragger.waitDrag=0;dragger.dragNode.pWindow=window;dragger.initFrameRoute()}if(dragger.dragNode.parentNode!=window.document.body){var a=dragger.gldragNode;if(dragger.gldragNode.old){a=dragger.gldragNode.old}a.parentNode.removeChild(a);var b=dragger.dragNode.pWindow;if(_isIE){var f=document.createElement("Div");f.innerHTML=dragger.dragNode.outerHTML;dragger.dragNode=f.childNodes[0]}else{dragger.dragNode=dragger.dragNode.cloneNode(true)}dragger.dragNode.pWindow=window;dragger.gldragNode.old=dragger.dragNode;document.body.appendChild(dragger.dragNode);b.dhtmlDragAndDrop.dragNode=dragger.dragNode}dragger.dragNode.style.left=c.clientX+15+(dragger.fx?dragger.fx*(-1):0)+(document.body.scrollLeft||document.documentElement.scrollLeft)+"px";dragger.dragNode.style.top=c.clientY+3+(dragger.fy?dragger.fy*(-1):0)+(document.body.scrollTop||document.documentElement.scrollTop)+"px";if(!c.srcElement){var d=c.target}else{d=c.srcElement}dragger.checkLanding(d,c.clientX,c.clientY)};dhtmlDragAndDropObject.prototype.calculateFramePosition=function(e){if(window.name){var c=parent.frames[window.name].frameElement.offsetParent;var d=0;var b=0;while(c){d+=c.offsetLeft;b+=c.offsetTop;c=c.offsetParent}if((parent.dhtmlDragAndDrop)){var a=parent.dhtmlDragAndDrop.calculateFramePosition(1);d+=a.split("_")[0]*1;b+=a.split("_")[1]*1}if(e){return d+"_"+b}else{this.fx=d}this.fy=b}return"0_0"};dhtmlDragAndDropObject.prototype.checkLanding=function(b,a,c){if((b)&&(b.dragLanding)){if(this.lastLanding){this.lastLanding.dragLanding._dragOut(this.lastLanding)}this.lastLanding=b;this.lastLanding=this.lastLanding.dragLanding._dragIn(this.lastLanding,this.dragStartNode,a,c)}else{if((b)&&(b.tagName!="BODY")){this.checkLanding(b.parentNode,a,c)}else{if(this.lastLanding){this.lastLanding.dragLanding._dragOut(this.lastLanding,a,c)}this.lastLanding=0}}};dhtmlDragAndDropObject.prototype.stopDrag=function(b,c){dragger=window.dhtmlDragAndDrop;if(!c){dragger.stopFrameRoute();var a=dragger.lastLanding;dragger.lastLanding=null;if(a){a.dragLanding._drag(dragger.dragStartNode,dragger.dragStartObject,a)}}dragger.lastLanding=null;if((dragger.dragNode)&&(dragger.dragNode.parentNode==document.body)){dragger.dragNode.parentNode.removeChild(dragger.dragNode)}dragger.dragNode=0;dragger.gldragNode=0;dragger.fx=0;dragger.fy=0;dragger.dragStartNode=0;dragger.dragStartObject=0;document.body.onmouseup=dragger.tempDOMU;document.body.onmousemove=dragger.tempDOMM;dragger.tempDOMU=null;dragger.tempDOMM=null;dragger.waitDrag=0};dhtmlDragAndDropObject.prototype.stopFrameRoute=function(b){if(b){window.dhtmlDragAndDrop.stopDrag(1,1)}for(var a=0;a<window.frames.length;a++){if((window.frames[a]!=b)&&(window.frames[a].dhtmlDragAndDrop)){window.frames[a].dhtmlDragAndDrop.stopFrameRoute(window)}}if((parent.dhtmlDragAndDrop)&&(parent!=window)&&(parent!=b)){parent.dhtmlDragAndDrop.stopFrameRoute(window)}};dhtmlDragAndDropObject.prototype.initFrameRoute=function(b,c){if(b){window.dhtmlDragAndDrop.preCreateDragCopy();window.dhtmlDragAndDrop.dragStartNode=b.dhtmlDragAndDrop.dragStartNode;window.dhtmlDragAndDrop.dragStartObject=b.dhtmlDragAndDrop.dragStartObject;window.dhtmlDragAndDrop.dragNode=b.dhtmlDragAndDrop.dragNode;window.dhtmlDragAndDrop.gldragNode=b.dhtmlDragAndDrop.dragNode;window.document.body.onmouseup=window.dhtmlDragAndDrop.stopDrag;window.waitDrag=0;if(((!_isIE)&&(c))&&((!_isFF)||(_FFrv<1.8))){window.dhtmlDragAndDrop.calculateFramePosition()}}if((parent.dhtmlDragAndDrop)&&(parent!=window)&&(parent!=b)){parent.dhtmlDragAndDrop.initFrameRoute(window)}for(var a=0;a<window.frames.length;a++){if((window.frames[a]!=b)&&(window.frames[a].dhtmlDragAndDrop)){window.frames[a].dhtmlDragAndDrop.initFrameRoute(window,((!b||c)?1:0))}}};var _isFF=false;var _isIE=false;var _isOpera=false;var _isKHTML=false;var _isMacOS=false;if(navigator.userAgent.indexOf("Macintosh")!=-1){_isMacOS=true}if((navigator.userAgent.indexOf("Safari")!=-1)||(navigator.userAgent.indexOf("Konqueror")!=-1)){_isKHTML=true}else{if(navigator.userAgent.indexOf("Opera")!=-1){_isOpera=true;_OperaRv=parseFloat(navigator.userAgent.substr(navigator.userAgent.indexOf("Opera")+6,3))}else{if(navigator.appName.indexOf("Microsoft")!=-1){_isIE=true}else{_isFF=true;var _FFrv=parseFloat(navigator.userAgent.split("rv:")[1])}}}function isIE(){if(navigator.appName.indexOf("Microsoft")!=-1){if(navigator.userAgent.indexOf("Opera")==-1){return true}}return false}dtmlXMLLoaderObject.prototype.doXPath=function(c,a){if((_isOpera)||(_isKHTML)){return this.doXPathOpera(c,a)}if(!a){if(!this.xmlDoc.nodeName){a=this.xmlDoc.responseXML}else{a=this.xmlDoc}}if(_isIE){return a.selectNodes(c)}else{var e=a;if(a.nodeName.indexOf("document")==-1){a=a.ownerDocument}var f=new Array();var b=a.evaluate(c,e,null,XPathResult.ANY_TYPE,null);var d=null;while(d=b.iterateNext()){f[f.length]=d}return f}};if((window.Node)&&(!_isKHTML)){Node.prototype.removeNode=function(c){var b=this;if(Boolean(c)){return this.parentNode.removeChild(b)}else{var a=document.createRange();a.selectNodeContents(b);return this.parentNode.replaceChild(a.extractContents(),b)}}}function _dhtmlxError(b,a,c){if(!this.catches){this.catches=new Array()}return this}_dhtmlxError.prototype.catchError=function(b,a){this.catches[b]=a};_dhtmlxError.prototype.throwError=function(b,a,c){if(this.catches[b]){return this.catches[b](b,a,c)}if(this.catches.ALL){return this.catches.ALL(b,a,c)}alert("Error type: "+arguments[0]+"\nDescription: "+arguments[1]);return null};window.dhtmlxError=new _dhtmlxError();dtmlXMLLoaderObject.prototype.doXPathOpera=function(c,a){var e=c.replace(/[\/]+/gi,"/").split("/");var d=null;var b=1;if(!e.length){return[]}if(e[0]=="."){d=[a]}else{if(e[0]==""){d=this.xmlDoc.responseXML.getElementsByTagName(e[b].replace(/\[[^\]]*\]/g,""));b++}else{return[]}}for(b;b<e.length;b++){d=this._getAllNamedChilds(d,e[b])}if(e[b-1].indexOf("[")!=-1){d=this._filterXPath(d,e[b-1])}return d};dtmlXMLLoaderObject.prototype._filterXPath=function(e,d){var g=new Array();var d=d.replace(/[^\[]*\[\@/g,"").replace(/[\[\]\@]*/g,"");for(var f=0;f<e.length;f++){if(e[f].getAttribute(d)){g[g.length]=e[f]}}return g};dtmlXMLLoaderObject.prototype._getAllNamedChilds=function(e,d){var h=new Array();for(var g=0;g<e.length;g++){for(var f=0;f<e[g].childNodes.length;f++){if(e[g].childNodes[f].tagName==d){h[h.length]=e[g].childNodes[f]}}}return h};