package com.epgis.csf.conventor;

import java.io.IOException;
import java.io.StringWriter;

import org.codehaus.jackson.JsonFactory;
import org.codehaus.jackson.JsonGenerator;
import org.codehaus.jackson.map.ObjectMapper;

import com.epgis.csf.exception.BusinessException;

/**
 * <p>Title: 消息转换器 </p>
 * <p>Description:</p>
 *
 * @author zhaoli
 * @version 1.0 2012-11-29
 */
public class JsonMessageConventor extends MessageConventor {
  /** 单例 */
  private static JsonMessageConventor instance = new JsonMessageConventor();
  
  private ObjectMapper mapper = null;
  
  
  public static JsonMessageConventor getInstacne(){
    return instance;
  }
  
  public ObjectMapper getObjectMapper(){
    if(mapper == null){
      mapper = new ObjectMapper();
      mapper.configure(org.codehaus.jackson.JsonParser.Feature.ALLOW_UNQUOTED_FIELD_NAMES, true);
      mapper.getDeserializationConfig().set(org.codehaus.jackson.map.DeserializationConfig.Feature.FAIL_ON_UNKNOWN_PROPERTIES, false);
    }
    return mapper;
  }
  
  public String marshaller(Class<?> class_type, Object obj) throws BusinessException{
//    Gson gson = getGson();
//    return gson.toJson(obj);
    mapper = getObjectMapper();
    StringWriter sw = new StringWriter();
    JsonGenerator gen;
    try {
      gen = new JsonFactory().createJsonGenerator(sw);
      mapper.writeValue(gen, obj);
      gen.close();
      return sw.toString();
    } catch (IOException e) {
      throw new BusinessException("-1", "JSON转换出错", e.getMessage());
    }
  }
  
  public Object unmarshaller(Class<?> class_type, String message) throws BusinessException{
//    Gson gson = getGson();
//    return gson.fromJson(message, TypeToken.get(class_type).getType());
    mapper = getObjectMapper();
    try {
      return mapper.readValue(message, class_type);
    } catch (IOException e) {
      throw new BusinessException("-1", "JSON转换出错", e.getMessage());
    }
  }
}
