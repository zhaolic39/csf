package net.csf.conventor;

import java.io.ByteArrayOutputStream;
import java.io.StringReader;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import javax.xml.bind.Unmarshaller;

import net.csf.exception.BusinessException;
import net.csf.utils.Constants;

/**
 * <p>Title: 消息转换器 </p>
 * <p>Description:</p>
 *
 * @author zhaoli
 * @version 1.0 2012-11-29
 */
public class XmlMessageConventor extends MessageConventor {
  /** 单例 */
  private static XmlMessageConventor instance = new XmlMessageConventor();
  
  /** 缓存JAXBContext */
  private static Map<Class<?>, JAXBContext> jaxbContextHashMap = new ConcurrentHashMap<Class<?>, JAXBContext>();

  public static XmlMessageConventor getInstacne(){
    return instance;
  }
  
  public Object unmarshaller(Class<?> class_type, String message) throws BusinessException{
    try{
      Unmarshaller unmarshaller = buildUnmarshaller(class_type);
      StringReader reader = new StringReader(message);
      Object o = unmarshaller.unmarshal(reader);
      return o;
    }
    catch(Exception e){
      throw new BusinessException("1", e.getMessage(), e.getMessage());
    }
  }
  
  /**
   * 创建Unmarshaller
   * @param class_type
   * @return
   * @throws JAXBException
   */
  private Unmarshaller buildUnmarshaller(Class<?> class_type) throws JAXBException{
    if(!jaxbContextHashMap.containsKey(class_type)){
      jaxbContextHashMap.put(class_type, JAXBContext.newInstance(class_type));
    }
    JAXBContext context = jaxbContextHashMap.get(class_type);
    Unmarshaller unmarshaller = context.createUnmarshaller();
    return unmarshaller;
  }
  
  public String marshaller(Class<?> class_type, Object obj) throws BusinessException{
    ByteArrayOutputStream baos = new ByteArrayOutputStream();
    try {
      Marshaller marshaller = buildmarshaller(class_type);
      marshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);
      marshaller.setProperty(Marshaller.JAXB_ENCODING, Constants.DEFAULT_CHARSET);
      marshaller.setProperty(Marshaller.JAXB_FRAGMENT, true);
      marshaller.marshal(obj, baos);
      return new String(baos.toByteArray(), Constants.DEFAULT_CHARSET);
    } catch (Exception e) {
      BusinessException ex = new BusinessException(e);
      ex.setErrorCode("1");
      ex.setErrorDesc(e.getMessage());
      ex.setErrorResult(e.getMessage());
      throw ex;
    }
  }
  
  /**
   * 创建Marshaller
   * @param class_type
   * @return
   * @throws JAXBException
   */
  private Marshaller buildmarshaller(Class<?> class_type) throws JAXBException{
    if(!jaxbContextHashMap.containsKey(class_type)){
      jaxbContextHashMap.put(class_type, JAXBContext.newInstance(class_type));
    }
    JAXBContext context = jaxbContextHashMap.get(class_type);
    Marshaller marshaller = context.createMarshaller();
    return marshaller;
  }
}
