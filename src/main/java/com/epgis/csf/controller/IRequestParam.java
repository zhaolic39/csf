package com.epgis.csf.controller;

import org.apache.commons.fileupload.FileItem;

/**
 * <p>Title: 服务请求参数接口 </p>
 * <p>Description:</p>
 *
 * @author zhaoli
 * @version 1.0 2013-10-28
 */
public interface IRequestParam {
  public void setMessageBody(String messageBody);
  
  public void setFileItem(FileItem fileItem);
}
