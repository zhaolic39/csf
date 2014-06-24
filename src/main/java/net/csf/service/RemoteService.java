package net.csf.service;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import net.csf.exception.BusinessException;
import net.csf.request.RequestMessage;
import net.csf.response.ResponseMessage;
import net.csf.utils.Constants;
import net.csf.utils.HttpClientUtils;
import net.csf.utils.MessageUtils;

import org.apache.commons.httpclient.HttpException;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**
 * <p>Title: 远程服务实现类 </p>
 * <p>Description:</p>
 *
 * @author zhaoli
 * @version 1.0 2013-10-24
 */
public class RemoteService implements IService {
  private final static  Log logger = (Log) LogFactory.getLog(MethodService.class);
  
  /** 服务名 */
  private String command_name;
  private String remote_command;
  
  /** 远程服务调用URL */
  private String service_url;
  /** 服务描述 */
  private String desc;

  public RemoteService(String url, String c, String remote_command, String d){
    service_url = url;
    command_name = c;
    desc = d;
    this.remote_command = remote_command;
  }
  
  public String getCommandName() {
    return command_name;
  }

  public String getServiceDesc() {
    return desc;
  }

  public Object doService(Object message, String format)throws BusinessException {
    RequestMessage requestMessage = (RequestMessage)message;
    requestMessage.setHttpCommand(remote_command);
    String remoteReq = MessageUtils.toRequestString(requestMessage, format);  //拼装接口报文
    
    //POST请求参数,报文与格式
    Map<String, String> param = new HashMap<String, String>();
    param.put(Constants.REQUEST, remoteReq);
    param.put(Constants.FORMAT, format);
    
    String httpRes = "";
    ResponseMessage responseMessage = null;
    try {
      httpRes = HttpClientUtils.doPost(service_url, param); //HTTP请求远程服务接口URL
      responseMessage = MessageUtils.toResponseObj(httpRes, format);  //解析返回报文，返回结果对象
    } catch (HttpException e) { //异常处理
      logger.error(e, e);
      throw new BusinessException("1", e.toString(), e.toString());
    } catch (IOException e) { //异常处理
      logger.error(e, e);
      throw new BusinessException("1", e.toString(), e.toString());
    }
    return responseMessage;
  }

  public String getImplementDesc() {
    return service_url;
  }

}
