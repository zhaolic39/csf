package com.epgis.csf.service;

import com.epgis.csf.exception.BusinessException;
import com.epgis.csf.request.RequestMessage;
import com.epgis.csf.response.ResponseMessage;

/**
 * <p>Title: HTTP命令接口(旧版本) </p>
 * <p>Description:</p>
 *
 * @author zhaoli
 * @version 1.0 2013-10-15
 */
public interface IHttpCommandHandler  {
	
	/**
	 * HTTP命令接口方法
	 * @param reqMessage
	 * @return
	 * @throws BusinessException
	 */
	public ResponseMessage messageHandle(RequestMessage reqMessage) throws BusinessException;

}
