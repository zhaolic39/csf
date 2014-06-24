package com.epgis.csf.utils;

import java.io.File;
import java.io.UnsupportedEncodingException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.FileUploadException;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;

/**
 * <p>Title:������� multipart/form-data</p>
 * <p>Description:</p>
 *
 * @author zhaoli
 * @version 1.0 2012-2-6
 */
public class MultiFormData {
  /** ��󻺴�ߴ� */
  public static int MAX_MEMORY_SIZE = 2 * 1024 * 1024;
  /** ����ϴ��ߴ� */
  public static int MAX_POST_SIZE = 50 * 1024 * 1024;
  
  private ServletFileUpload servletFileUpload = null;
  
  private Map<String, Object> params = new HashMap<String, Object>(); 
  
  public MultiFormData(HttpServletRequest request) throws FileUploadException, UnsupportedEncodingException{
    DiskFileItemFactory factory = new DiskFileItemFactory();
    factory.setSizeThreshold(MAX_MEMORY_SIZE);
    factory.setRepository(new File(System.getProperty("java.io.tmpdir")));
    servletFileUpload = new ServletFileUpload(factory);
    servletFileUpload.setSizeMax(MAX_POST_SIZE);
    request.setCharacterEncoding(Constants.DEFAULT_CHARSET);
    
    List<FileItem> items = servletFileUpload.parseRequest(request);
    for(FileItem item:items){
      if(item.isFormField()){ //��ͨ����
        String field_name = item.getFieldName();
        String field_value = item.getString(Constants.DEFAULT_CHARSET);
        params.put(field_name, field_value);
      }
      else{ //�ļ�����
        String field_name = item.getFieldName();
        params.put(field_name, item);
      }
    }
  }
  
  /**
   * �����ͨ�ֶ�ֵ
   * @param param_name
   * @return
   */
  public String getParameter(String param_name){
    return (String)params.get(param_name);
  }
  
  /**
   * ����ϴ��ļ���FileItem
   * @param param_name
   * @return
   */
  public FileItem getUploadFile(String param_name){
    return (FileItem)params.get(param_name);
  }
  
  /**
   * ����ϴ��ļ����ļ���
   * @param param_name
   * @return
   */
  public String getFileName(String param_name){
    FileItem item = getUploadFile(param_name);
    return item.getName();
  }
  
  /**
   * ����ϴ��ļ����ֽ�
   * @param param_name
   * @return
   */
  public byte[] getFileByte(String param_name){
    FileItem item = getUploadFile(param_name);
    return item.get();
  }
}
