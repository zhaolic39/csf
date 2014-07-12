package net.csf.controller;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;

import net.csf.request.IRequestParam;

import org.apache.commons.fileupload.FileItem;

/**
 * <p>Title: JSON格式服务的通用请求参数 </p>
 * <p>Description:</p>
 *
 * @author zhaoli
 * @version 1.0 2013-10-28
 */
public class JsonRequestParam implements Map<String, Object>, IRequestParam {
  private String body;
  private FileItem file;
  private Map<String, Object> bodyMap = new HashMap<String, Object>();
  
  public String getString(String key){
    Object o = this.get(key);
    if(o == null) return "";
    return o.toString();
  }
  
  public void setMessageBody(String messageBody) {
    body = messageBody;
  }
  
  public String getMessageBody() {
    return body;
  }
  
  public void setFileItem(FileItem fileItem) {
    file = fileItem;
  }
  
  public FileItem getFileItem() {
    return file;
  }

  public int size() {
    return bodyMap.size();
  }

  public boolean isEmpty() {
    return bodyMap.isEmpty();
  }

  public boolean containsKey(Object key) {
    return bodyMap.containsKey(key);
  }

  public boolean containsValue(Object value) {
    return bodyMap.containsValue(value);
  }

  public Object get(Object key) {
    return bodyMap.get(key);
  }

  public Object put(String key, Object value) {
    return bodyMap.put(key, value);
  }

  public Object remove(Object key) {
    return bodyMap.remove(key);
  }

  public void putAll(Map<? extends String, ? extends Object> t) {
    bodyMap.putAll(t);
  }

  public void clear() {
    bodyMap.clear();
  }

  public Set<String> keySet() {
    return bodyMap.keySet();
  }

  public Collection<Object> values() {
    return bodyMap.values();
  }

  public Set<java.util.Map.Entry<String, Object>> entrySet() {
    return bodyMap.entrySet();
  }

  public void setHttpRequest(HttpServletRequest request) {
    
  }


}
