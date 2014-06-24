package net.csf.conventor;

import javax.xml.bind.JAXBException;

import net.csf.exception.BusinessException;
import net.csf.utils.Constants;

import org.apache.commons.lang.StringUtils;

/**
 * <p>Title: 消息转换器 </p>
 * <p>Description:</p>
 *
 * @author zhaoli
 * @version 1.0 2012-11-29
 */
public abstract class MessageConventor {
  /**
   * 工厂方法
   * @return
   */
  public static MessageConventor getInstacne(String format){
    if(StringUtils.equals(format, Constants.JSON)){
      return JsonMessageConventor.getInstacne();
    }
    else{
      return XmlMessageConventor.getInstacne();
    }
  }
  
  /**
   * 将报文转为消息对象
   * @param class_type
   * @param message
   * @return
   * @throws JAXBException
   */
  public abstract Object unmarshaller(Class<?> class_type, String message) throws BusinessException;
  
  
  /**
   * 将对象转为报文
   * @param class_type
   * @param obj
   * @return
   * @throws JAXBException
   */
  public abstract String marshaller(Class<?> class_type, Object obj) throws BusinessException;
  
}
