function dhtmlXTreeFromHTML(k){if(typeof(k)!="object"){k=document.getElementById(k)}var d=k;var c=d.id;var s="";for(var f=0;f<k.childNodes.length;f++){if(k.childNodes[f].nodeType=="1"){if(k.childNodes[f].tagName=="XMP"){var o=k.childNodes[f];for(var e=0;e<o.childNodes.length;e++){s+=o.childNodes[e].data}}else{if(k.childNodes[f].tagName.toLowerCase()=="ul"){s=dhx_li2trees(k.childNodes[f],new Array(),0)}}break}}k.innerHTML="";var r=new dhtmlXTreeObject(k,"100%","100%",0);var h=new Array();for(b in r){h[b.toLowerCase()]=b}var q=k.attributes;for(var p=0;p<q.length;p++){if((q[p].name.indexOf("set")==0)||(q[p].name.indexOf("enable")==0)){var l=q[p].name;if(!r[l]){l=h[q[p].name]}r[l].apply(r,q[p].value.split(","))}}if(typeof(s)=="object"){r.XMLloadingWarning=1;for(var g=0;g<s.length;g++){var d=r.insertNewItem(s[g][0],(g+1),s[g][1]);if(s[g][2]){r._setCheck(d,true)}}r.XMLloadingWarning=0;r.lastLoadedXMLId=0;r._redrawFrom(r)}else{r.loadXMLString("<tree id='0'>"+s+"</tree>")}window[c]=r;return r}function dhx_init_trees(){var c=document.getElementsByTagName("div");for(var a=0;a<c.length;a++){if(c[a].className=="dhtmlxTree"){dhtmlXTreeFromHTML(c[a])}}}function dhx_li2trees(n,g,d){for(var h=0;h<n.childNodes.length;h++){var m=n.childNodes[h];if((m.nodeType==1)&&(m.tagName.toLowerCase()=="li")){var l="";var k=null;var a=m.getAttribute("checked");for(var f=0;f<m.childNodes.length;f++){var e=m.childNodes[f];if(e.nodeType==3){l+=e.data}else{if(e.tagName.toLowerCase()!="ul"){l+=e.outerHTML?e.outerHTML:e.innerHTML}else{k=e}}}g[g.length]=[d,l,a];if(k){g=dhx_li2trees(k,g,g.length)}}}return g}if(window.addEventListener){window.addEventListener("load",dhx_init_trees,false)}else{if(window.attachEvent){window.attachEvent("onload",dhx_init_trees)}};
