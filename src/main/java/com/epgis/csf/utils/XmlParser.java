package com.epgis.csf.utils;

import java.io.StringReader;
import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;
import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;

/**
 * ��˵�������ڶ�ȡXML����ͨ��path��ʽ����Ԫ�صĹ�����.
 * @author ������
 * @date 2011 2011-5-6 ����04:57:55
 * @version 1.1 �޸�BUG��getValue���״? by zhaoli 2012-1-6
 */
public class XmlParser {
	private String xmlBody = null;
	
  private static Logger log = Logger.getLogger(XmlParser.class);

	public XmlParser(String xmlBody){
		this.xmlBody = xmlBody;
		load();
	}
 
	private  Element rootElement = null;
	
	public static void main(String[] args) {
		String str = "<content>" +
				           "<message_data>" +
				             "<message>" +
				               "<text>��</text>" +
				               "<send_time>1111</send_time>" +
				               "<send_user_name>��</send_user_name>" +
				             "</message>" +
                     "<message>" +
                     "<text>��</text>" +
                     "<send_time>2222</send_time>" +
                     "<send_user_name>��</send_user_name>" +
                   "</message>" +
				           "</message_data>" +
				         "</content>";
		
		XmlParser par = new XmlParser(str);	    
		List<Element> eles = par.getElements("message_data.message");
		System.out.println(eles.size());
		for(Element ele:eles){
		  System.out.println(ele.element("text").getTextTrim());
		  System.out.println(ele.element("send_time").getTextTrim());
		  System.out.println(ele.element("send_user_name").getTextTrim());
		}
	}
	
	/**
	 * 获得根节点
	 * @return
	 */
	public Element getRootElement(){
	  return rootElement;
	}
	
	public void loadByListElement(List<Element> list,String root){
		StringBuffer xml=new StringBuffer();
		xml.append("<"+root+">");
		for(int i = 0; i < list.size(); i++){
			xml.append(list.get(i).asXML());
		}
		xml.append("</"+root+">");
		xmlBody=xml.toString();
	}
 	private void load()  {
		if (rootElement == null) {
			SAXReader reader = new SAXReader();
			Document doc = null;
			try {
				doc = reader.read(new StringReader(xmlBody));
				rootElement = doc.getRootElement();
			} catch (DocumentException e) {
				log.error(e);
			}
		}
	}

	/**
	 * 
	 * @param xpath
	 *            ��,dxp.JMS.ip
	 * @return
	 * @throws Exception
	 */
	public  List<Element> getElements(String xpath)  {
		try {
			if (rootElement == null) {
				load();
			}
			xpath = xpath.replace('.', '/');
			return rootElement.selectNodes(xpath);
		} catch (Exception e) {
				log.error(e, e);
		}
		return null;
	}

	/**
 	 * @param xpath
	 *            ��,dxp.JMS.ip
	 */
	public  Element getElement(String xpath)  {
		if (rootElement == null) {
			load();
		}
		xpath = xpath.replace('.', '/');
		List eltLst = null;
		try{
		  eltLst = rootElement.selectNodes(xpath);
		}catch(Exception e){
		  eltLst = null;
		}
		
		if (eltLst == null || eltLst.size() == 0) {
		  return null;
		}
		return (Element) eltLst.get(0);
	}

	/**
	 * 
	 * @param xpath   XML����xpath·��
	 * @param attName  Ҫ�ҵ�xpath�µ�������
	 * @param attValue Ҫ�ҵ�xpath�µ�����ֵ
	 * @return  �������attName=attValue��Ԫ��
	 * @throws Exception
	 */
	public  Element getElement(String xpath,String attName, String attValue)
			 {
		if (rootElement == null) {
			load();
		}
		xpath = xpath.replace('.', '/');
		xpath = xpath.concat("[@"+attName+"='" + attValue + "']");
		List eltLst = rootElement.selectNodes(xpath);
		if (eltLst == null || eltLst.size() == 0) {
		  return null;
		}
		return (Element) eltLst.get(0); 
	}
	 
	/**
	 * ���ֵ
	 * @param xpath ��'.'�ָ�
	 * @return
	 * @throws Exception
	 */
	public String getValue(String xpath)  {
		Element elt = getElement(xpath);
		if(elt != null){
		  return elt.getTextTrim(); 
		}
		return "";
	}

	/**
	 * 
	 * @param xpath ��'.'�ָ�
	 * @param attValue
	 * @return
	 * @throws Exception
	 */
	public  String getValue(String xpath, String attName ,String attValue){
		Element elt = getElement(xpath, attName, attValue);
		if(elt != null)
		  return elt.getTextTrim();
		return "";
	}
	public String toString(){
		return xmlBody;
	}
}
