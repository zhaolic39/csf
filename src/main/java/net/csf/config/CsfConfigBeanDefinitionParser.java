package net.csf.config;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.beans.factory.config.RuntimeBeanReference;
import org.springframework.beans.factory.parsing.BeanComponentDefinition;
import org.springframework.beans.factory.parsing.CompositeComponentDefinition;
import org.springframework.beans.factory.support.RootBeanDefinition;
import org.springframework.beans.factory.xml.BeanDefinitionParser;
import org.springframework.beans.factory.xml.ParserContext;
import org.w3c.dom.Element;

/**
 * <p>Title: CSF远程服务配置解析器 </p>
 * <p>Description:</p>
 *
 * @author zhaoli
 * @version 1.0 2013-10-24
 */
public class CsfConfigBeanDefinitionParser implements BeanDefinitionParser {
  private static Log logger = (Log) LogFactory.getLog(CsfConfigBeanDefinitionParser.class);
  
  private static CsfConfigBeanDefinitionParser instance = new CsfConfigBeanDefinitionParser();
  
  public static CsfConfigBeanDefinitionParser getInstance(){
    return instance;
  }
  
  private CsfConfigBeanDefinitionParser(){}
  
  public BeanDefinition parse(Element element, ParserContext parserContext) {
    Object source = parserContext.extractSource(element);
    CompositeComponentDefinition compDefinition = new CompositeComponentDefinition(element.getTagName(), source);
    parserContext.pushContainingComponent(compDefinition);
    
    RootBeanDefinition csfConfigBean = new RootBeanDefinition(CsfConfig.class);
    String serviceRouterName = parserContext.getReaderContext().registerWithGeneratedName(csfConfigBean);
    parserContext.registerComponent(new BeanComponentDefinition(csfConfigBean, serviceRouterName));
    
    RuntimeBeanReference requestParserRef = new RuntimeBeanReference(element.getAttribute("request-parser"));
    if (logger.isInfoEnabled()) {
        logger.info("装配自定义的requestParser:" + requestParserRef.getBeanName());
    }
    csfConfigBean.getPropertyValues().add("requestParser", requestParserRef);
    System.out.println("add value requestParser");
    
    String interceptorClass = element.getAttribute("request-interceptor"); //请求拦截处理器
    
    parserContext.popAndRegisterContainingComponent();
    return null;
  }
}
