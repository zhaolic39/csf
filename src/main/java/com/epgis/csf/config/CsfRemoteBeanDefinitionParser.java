package com.epgis.csf.config;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.httpclient.HttpException;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.codehaus.jackson.JsonNode;
import org.codehaus.jackson.map.ObjectMapper;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.beans.factory.xml.BeanDefinitionParser;
import org.springframework.beans.factory.xml.ParserContext;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import com.epgis.csf.conventor.JsonMessageConventor;
import com.epgis.csf.utils.HttpClientUtils;

/**
 * <p>Title: CSF远程服务配置解析器 </p>
 * <p>Description:</p>
 *
 * @author zhaoli
 * @version 1.0 2013-10-24
 */
public class CsfRemoteBeanDefinitionParser implements BeanDefinitionParser {
  private static Log logger = (Log) LogFactory.getLog(CsfRemoteBeanDefinitionParser.class);
  
  /** 默认的远程服务前缀 */
  public static final String DEFAULT_PREFIX = "REMOTE";
  
  private static CsfRemoteBeanDefinitionParser instance = new CsfRemoteBeanDefinitionParser();
  
  private List<RemoteServiceConfig> config_list = new ArrayList<RemoteServiceConfig>();
  
  private List<Map<String, String>> url_list = new ArrayList<Map<String, String>>();
  
  public static CsfRemoteBeanDefinitionParser getInstance(){
    return instance;
  }
  
  private CsfRemoteBeanDefinitionParser(){}
  
  public BeanDefinition parse(Element element, ParserContext parserContext) {
    String remote_url = element.getAttribute("remote-url"); //远程服务URL
    String config_url = element.getAttribute("config-url"); //远程服务配置列表url
    String prefix = element.getAttribute("prefix"); //远程服务前缀
    if(StringUtils.isEmpty(prefix)){
      prefix = DEFAULT_PREFIX;
    }
    
    if(!StringUtils.isEmpty(config_url)){
      Map<String, String> m = new HashMap<String, String>();
      m.put("config_url", config_url);
      m.put("prefix", prefix);
      url_list.add(m);
    }
    
    NodeList list = element.getElementsByTagNameNS("*", "service"); //配置的服务列表
    for(int i=0;i<list.getLength();i++){
      Node node = list.item(i);
      Element ele = (Element)node;
      String command = ele.getAttribute("command");
      String desc = ele.getAttribute("desc");
      
      RemoteServiceConfig config = new RemoteServiceConfig(remote_url, prefix + "." + command, command, desc);
      config_list.add(config);
    }
    return null;
  }
  
  /**
   * 读取远程服务配置URL
   */
  public void loadConfig(){
    for(Map<String, String> m:url_list){
      String url = m.get("config_url");
      try {
        String configJson = HttpClientUtils.doPost(url, null);  //配置JSON数据
        ObjectMapper mapper = JsonMessageConventor.getInstacne().getObjectMapper();
        JsonNode rootNode = mapper.readTree(configJson);
        System.out.println(rootNode.size());
      } catch (HttpException e) {
        logger.error(e, e);
      } catch (IOException e) {
        logger.error(e, e);
      }
    }
  }
  
  public List<RemoteServiceConfig> getRemoteConfigList(){
    return config_list;
  }
}
