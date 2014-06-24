package com.epgis.csf.service;

import com.epgis.csf.exception.BusinessException;

/**
 * <p>Title: 服务的抽象接口</p>
 * <p>Description:</p>
 *
 * @author zhaoli
 * @version 1.0 2013-1-31
 */
public interface IService {
  /**
   * command命令名
   * @return
   */
  public String getCommandName();
  
  /**
   * 服务描述
   * @return
   */
  public String getServiceDesc();
  
  /**
   * 服务实现
   * @throws  
   * @throws Exception 
   */
  public Object doService(Object message, String format) throws BusinessException;
  
  /**
   * 服务实现描述
   * @return
   */
  public String getImplementDesc();
}
