package com.epgis.csf.service;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.epgis.csf.controller.IRequestParam;
import com.epgis.csf.conventor.MessageConventor;
import com.epgis.csf.exception.BusinessException;
import com.epgis.csf.request.RequestMessage;
import com.epgis.csf.response.ResponseMessage;
import com.epgis.csf.utils.Constants;
import com.epgis.csf.utils.MessageUtils;

/**
 * <p>Title: 封装HttpService服务的类，服务方法只能接收一个参数 </p>
 * <p>Description:</p>
 *
 * @author zhaoli
 * @version 1.0 2012-11-28
 */
public class MethodService implements LocalService{
  private final static  Log logger = (Log) LogFactory.getLog(MethodService.class);
  
  /** command_name */
  private String command_name;
  /** 服务提供实例 */
  private Object service_object;
  /** 服务提供方法 */
  private Method service_method;
  /** 服务描述 */
  private String desc = "";
  /** 服务方法参数类型 */
  private Class<?> method_param_type = null;
  /** 服务方法返回类型 */
  private Class<?> method_return_type = null;

  public MethodService(String command_name, Object service_object, Method service_method){
    this.command_name = command_name;
    this.service_object = service_object;
    this.service_method = service_method;
    initService();
  }
  
  /**
   * 初始化服务信息
   */
  private void initService(){
    method_param_type = service_method.getParameterTypes()[0];
    method_return_type = service_method.getReturnType();
  }
  
  public void setServiceDesc(String desc){
    this.desc = desc;
  }

  public String getCommandName() {
    return command_name;
  }

  public String getServiceDesc() {
    return desc;
  }

  public Object doService(Object message, String format) throws BusinessException{
    try {
      RequestMessage requestMessage = (RequestMessage)message;
      IRequestParam requestObj = (IRequestParam)buildRequest(requestMessage, format);
      requestObj.setMessageBody(requestMessage.getHttpCommandBody());
      requestObj.setFileItem(requestMessage.getFileItem()); //附件内容
      Object resultObj = reflectInvoke(requestObj);
      String content = buildResponse(resultObj, format);
      ResponseMessage responseMessage = MessageUtils.createSuccResponse(content);
      return responseMessage;
    } 
    catch (InvocationTargetException e) {
      if(e.getTargetException() instanceof BusinessException){
        BusinessException httpe = (BusinessException)e.getTargetException();
        throw new BusinessException(httpe.getErrorCode(), httpe.getErrorDesc(), httpe.getErrorResult());
      }
      logger.error(e, e);
      throw new BusinessException("1", e.getTargetException().toString(), e.getTargetException().toString());
    } catch (Exception e) {
      logger.error(e, e);
      throw new BusinessException("1", e.getMessage(), e.getMessage());
    }
  }
  
  /**
   * 反射调用服务方法 
   * @param requestObj
   * @return
   * @throws IllegalArgumentException
   * @throws IllegalAccessException
   * @throws InvocationTargetException
   */
  public Object reflectInvoke(Object requestObj) throws IllegalArgumentException, IllegalAccessException, InvocationTargetException{
    Object resultObj = service_method.invoke(service_object, requestObj);
    return resultObj;
  }

  /**
   * 构造请求参数对象
   * @param requestMessage
   * @return
   * @throws IllegalAccessException 
   * @throws InstantiationException 
   */
  private Object buildRequest(RequestMessage requestMessage, String format) throws InstantiationException, IllegalAccessException {
    Object requestObj = null;
    try{
      if(StringUtils.isEmpty(requestMessage.getHttpCommandBody())){ //没有输入参数，new一个空参数
        requestObj = method_param_type.newInstance();
      }
      else{
        requestObj = MessageConventor.getInstacne(format).unmarshaller(method_param_type, requestMessage.getHttpCommandBody());
      }
    }
    catch (BusinessException e) {
      logger.error(e, e);
      requestObj = method_param_type.newInstance();
    }
    return requestObj;
  }
  
  /**
   * 将结果转为字符串消息
   * @param response
   * @return
   * @throws BusinessException 
   */
  private String buildResponse(Object response, String format) throws BusinessException{
    if(response == null){
      if(StringUtils.equals(format, Constants.JSON))
        return "";
      else return "<content></content>";
    }
    return MessageConventor.getInstacne(format).marshaller(method_return_type, response);
  }

  public String getImplementDesc() {
    return service_method.getDeclaringClass().getCanonicalName() + "#" + service_method.getName() + "(..)";
  }
}
