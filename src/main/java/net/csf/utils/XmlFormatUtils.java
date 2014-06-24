package net.csf.utils;

import java.io.IOException;
import java.io.StringWriter;

import org.dom4j.DocumentException;
import org.dom4j.DocumentHelper;
import org.dom4j.io.OutputFormat;
import org.dom4j.io.XMLWriter;

/**
 * <p>Title: XML������</p>
 * <p>Description:</p>
 *
 * @author zhaoli
 * @version 1.0 2011-12-30
 */
public class XmlFormatUtils {
    /**
   * �����xml���ַ������������������ַ��������xml�ļ��е�Ч��һ��
   * @param str
   * @return
   */
  public static String strChangeToXML(String str, String charSet) throws DocumentException {
     org.dom4j.Document document = null;
     try {  
         document = DocumentHelper.parseText(str);
     } catch (DocumentException documentexception) {
         //xml�ı���ʽ����
  //       documentexception.printStackTrace();
         throw documentexception;
     }
     OutputFormat outputformat = OutputFormat.createPrettyPrint();
     // �������ڿ���xml�����ͷ��Ϣ(�磺<?xml version="1.0" encoding="UTF-8"?>)��true ��ʾ������� false ��ʾ���
     outputformat.setSuppressDeclaration(true);
     outputformat.setEncoding(charSet);
     StringWriter stringwriter = new StringWriter();
     XMLWriter xmlwriter = new XMLWriter(stringwriter, outputformat);
     try {
         xmlwriter.write(document);
     } catch (IOException e) {
     }
     return stringwriter.toString().trim();
  }
  
    /**
   * ��ʽ��XML�ַ�
   * @param str
   * @return
   */
  public static String formatXmlStr(String str, String charSet){
    try{
      return strChangeToXML(str, charSet);
    }
    catch(Exception e){
      return str;
    }
  }
  
  public static String formatXmlStr(String str){
    return formatXmlStr(str, Constants.DEFAULT_CHARSET);
  }
}
