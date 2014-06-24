package net.csf.service;

import net.csf.exception.BusinessException;
import net.csf.request.RequestMessage;

/**
 * <p>Title: HttpHandler服务封装 </p>
 * <p>Description:</p>
 *
 * @author zhaoli
 * @version 1.0 2012-11-28
 */
public class HttpHandlerService implements LocalService{
  /** HTTP命令名*/
  private String command_name;
  
  /** HTTP命令描述 */
  private String desc;
  
  /** HTTP命令处理器*/
  private IHttpCommandHandler httpCommandHandler;

  public HttpHandlerService(String command_name, IHttpCommandHandler handler, String desc){
    this.command_name = command_name;
    httpCommandHandler = handler;
    this.desc = desc;
  }

  public String getCommandName() {
    return command_name;
  }

  public String getServiceDesc() {
    return desc;
  }

  public String getImplementDesc() {
    return httpCommandHandler.getClass().getName();
  }

  public Object doService(Object message, String format) throws BusinessException {
    try {
      return httpCommandHandler.messageHandle((RequestMessage)message);
    } catch (BusinessException e) {
      throw new BusinessException(e.getErrorCode(), e.getErrorDesc(), e.getErrorResult());
    }
  }

}
