package net.csf.controller;

import javax.servlet.http.HttpServletRequest;
import javax.xml.bind.annotation.XmlTransient;

import net.csf.request.IRequestParam;

import org.apache.commons.fileupload.FileItem;


/**
 * <p>Title: ������������ԭʼ��Ϣ����Ϣ </p>
 * <p>Description:</p>
 *
 * @author zhaoli
 * @version 1.0 2012-11-29
 */
public class ServiceParam implements IRequestParam{
  /** ԭʼ��Ϣ���� */
  private String messageBody;
  
  /** �������� */
  private FileItem fileItem;

  @XmlTransient
  public FileItem getFileItem() {
    return fileItem;
  }

  public void setFileItem(FileItem fileItem) {
    this.fileItem = fileItem;
  }

  public String getMessageBody() {
    return messageBody;
  }

  public void setMessageBody(String messageBody) {
    this.messageBody = messageBody;
  }

  public void setHttpRequest(HttpServletRequest request) {
    
  }
}
