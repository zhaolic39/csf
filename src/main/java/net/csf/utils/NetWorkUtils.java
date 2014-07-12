package net.csf.utils;

import java.io.IOException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.codehaus.jackson.map.ObjectMapper;

/**
 * 网络相关工具类
 * @author zhaolic39
 */
public class NetWorkUtils {
  private static ObjectMapper objectMapper = new ObjectMapper();
  
  static{
    java.text.DateFormat format1 = new java.text.SimpleDateFormat("yyyy-MM-dd HH:mm:ss");  
    objectMapper.getSerializationConfig().setDateFormat(format1);
    objectMapper.configure(org.codehaus.jackson.JsonParser.Feature.ALLOW_UNQUOTED_FIELD_NAMES, true);
    objectMapper.getDeserializationConfig().set(org.codehaus.jackson.map.DeserializationConfig.Feature.FAIL_ON_UNKNOWN_PROPERTIES, false);
  }
  
  public static String getRealIp(HttpServletRequest request){
    String ip = request.getHeader("X-Real-IP"); 
//    String ip = request.getHeader("x-forwarded-for");  
    if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {  
        ip = request.getHeader("Proxy-Client-IP");  
    }  
    if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {  
        ip = request.getHeader("WL-Proxy-Client-IP");  
    }  
    if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {  
        ip = request.getRemoteAddr();  
    }  
    return ip;
  }
  
  /**
   * 输出结果
   * @param response
   * @param result
   * @throws IOException
   */
  public static void writeResponse(HttpServletResponse response, String format, String result) throws IOException{
    response.setContentType(format + "; charset=" + Constants.DEFAULT_CHARSET);
    response.setCharacterEncoding(Constants.DEFAULT_CHARSET);
    response.getWriter().print(result);
  }
  
  public static void write(HttpServletResponse response, String result) throws IOException{  
    writeResponse(response, "text/plain", result);
  }
  
  public static void writeXml(HttpServletResponse response, String result) throws IOException{  
    writeResponse(response, "text/xml", result);
  }
  
  public static String getJson(Object obj){
    String json = "";
    try {
      json = objectMapper.writeValueAsString(obj);
    } catch (Exception e) {
      e.printStackTrace();
    }
    return json;
  }
}
