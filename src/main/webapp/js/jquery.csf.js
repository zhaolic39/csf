(function(){
  var agent = function(){
    
    var showMsg = function(msg){
      //getBaskHintDialog().hintMsg(msg);
      alert(msg);
    };
    
    /**
     * @param transport:AjaxEngine的返回对象
     * */
    var handleResult = function(content, callBack, options){
      //alert(customContent.msg);
      if(content.respCode == "0"){
        if(callBack){
          callBack(content);
        }else{
          showMsg("操作成功！");
        }
      }
      else{ //业务上出错
        if(content.result_code==null || options.ignoreError){//自己处理
          if(callBack) callBack(content);
        }
        else{ //统一处理
          showMsg(content.respResult + (content.respDesc?('\n'+content.respDesc):''));
        }
      }
    };
    
    /** 
     * 调用服务端
     * @param url:服务url
     * @param params_str:参数
     * @param callback:回调函数
     * @param options.ignoreError 是否忽略错误，true由callBack自己处理result_code为0的逻辑
     * @param options.hideWaitDialog 是否隐藏WaitDialog
     * @param options.asynchronous 是否异步调用
     */
    var invoke = function(command, param, callBack, options){
      if(!options) options = {};
      if(options.asynchronous == null) options.asynchronous = true;
      
      if(!options.hideWaitDialog){
        //getBaskHintDialog().waitDialog("正在处理...", "", "提示");
      }
      
      var requestJson = {httpCommand:command, requestDate:"", verifyCode:"", httpCommandBody:param};
      var jsonparam = $.toJSON(requestJson);
      var param_str = "format=JSON&request=" + jsonparam;
      
      var ajaxurl = AppConfig.WEB_CONTEXT_PATH + "/api/httpCommand.jsp" ;
      $.ajax({
          type:'POST',
          url:ajaxurl,
          data:param_str,
          dataType:'json',
          success:function(msg){
            if(msg){
              handleResult(msg, callBack, options);
            }
            else{
              callBack();
            }
          }
      });
    };
    
    return {
      invoke:invoke
    };
  };
  $.CsfAgent = new agent();
})();