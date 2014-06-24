package net.csf.utils;

import java.io.IOException;
import java.util.Date;

import net.csf.conventor.JsonMessageConventor;
import net.csf.request.RequestMessage;
import net.csf.response.ResponseMessage;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.time.DateFormatUtils;
import org.codehaus.jackson.JsonNode;
import org.codehaus.jackson.JsonProcessingException;
import org.codehaus.jackson.map.ObjectMapper;
import org.dom4j.Element;

/**
 * <p>Title: 服务消息转换工具类 </p>
 * <p>Description:</p>
 *
 * @author zhaoli
 * @version 1.0 2013-2-1
 */
public class MessageUtils {
  
  public static void main(String[] args) throws JsonProcessingException, IOException {
    String str = "<request><httpCommand>sdfsdf</httpCommand>  "
        + "  <httpCommandBody><content><a>aaa</a><b>bbb</b></content></httpCommandBody>    <requestDate>2011202022</requestDate> "
        + "   <verifyCode>verffyCode</verifyCode></request>";
    
    RequestMessage msg = toRequestMessageFromXml(str);
    System.out.println(msg.toString()); 
    
    String aa = "{\"httpCommand\":\"abcd\", \"requestDate\":\"20011233\", \"verifyCode\":\"adfwerwer\", \"httpCommandBody\":{\"id\":1,\"name\":\"name1\",\"desc\":\"desc123\"}}";
    msg = toRequestMessageFromJson(aa);
    System.out.println(msg.toString()); 
  }

  /**
   * 请求报文转为消息对象
   * @param reqMsg
   * @param format
   * @return
   * @throws IOException 
   * @throws JsonProcessingException 
   */
  public static RequestMessage toRequestMessage(String reqMsg, String format) throws JsonProcessingException, IOException{
    if(StringUtils.equals(format, Constants.JSON)){
      return toRequestMessageFromJson(reqMsg);
    }
    else{
      return toRequestMessageFromXml(reqMsg);
    }
  }
  
  /**
   * xml报文转换
   * @param reqMesg
   * @return
   */
  public static RequestMessage toRequestMessageFromXml(String reqMsg){
    XmlParser parser = new XmlParser(reqMsg);
    RequestMessage msg = new RequestMessage();
    msg.setHttpCommand(parser.getValue("httpCommand"));
    msg.setRequestDate(parser.getValue("requestDate"));
    msg.setVerifyCode(parser.getValue("verifyCode"));
    Element ele = parser.getElement("httpCommandBody.content");
    if(ele != null) msg.setHttpCommandBody(ele.asXML());
    return msg;
  }
  
  /**
   * json报文转换
   * @param reqMesg
   * @return
   * @throws IOException 
   * @throws JsonProcessingException 
   */
  public static RequestMessage toRequestMessageFromJson(String reqMsg) throws JsonProcessingException, IOException{
    ObjectMapper mapper = JsonMessageConventor.getInstacne().getObjectMapper();
    JsonNode rootNode = mapper.readTree(reqMsg);
    RequestMessage msg = new RequestMessage();
    msg.setHttpCommand(rootNode.path("httpCommand").getTextValue());
    msg.setRequestDate(rootNode.path("requestDate").getTextValue());
    msg.setVerifyCode(rootNode.path("verifyCode").getTextValue());
    msg.setHttpCommandBody(rootNode.path("httpCommandBody").toString());
    return msg;
  }
  
  public static String toResponseMessage(ResponseMessage response, String format){
    if(StringUtils.equals(format, Constants.JSON)){
      return toResponseMessageJson(response);
    }
    else{
      return toResponseMessageXml(response);
    }
  }
  
  /**
   * 对象转为XML报文
   * @param response
   * @return
   */
  public static String toResponseMessageXml(ResponseMessage response){
    StringBuffer resXml = new StringBuffer("<?xml version=\"1.0\" encoding=\"UTF-8\"?><response>");
    resXml.append("<respResult>").append(response.getRespResult()).append("</respResult>")
          .append("<respCode>").append(response.getRespCode()).append("</respCode>")
          .append("<respDate>").append(response.getRespDate()).append("</respDate>")
          .append("<respDesc>").append(response.getRespDesc()).append("</respDesc>")
          .append("<respBody>").append(response.getRespBody()).append("</respBody>")
          .append("</response>");
    return resXml.toString();
  }
  
  /**
   * 对象转为JSON报文
   * @param response
   * @return
   */
  public static String toResponseMessageJson(ResponseMessage response){
    StringBuffer resXml = new StringBuffer("{");
    resXml.append("\"respResult\":\"").append(response.getRespResult()).append("\",")
          .append("\"respCode\":\"").append(response.getRespCode()).append("\",")
          .append("\"respDate\":\"").append(response.getRespDate()).append("\",")
          .append("\"respDesc\":\"").append(response.getRespDesc()).append("\",")
          .append("\"respBody\":").append(StringUtils.isEmpty(response.getRespBody())?"{}":response.getRespBody()).append("}");
    return resXml.toString();
  }
  
  /**
   * 请求对象转为请求字符串
   * @param requestMessage
   * @param format
   * @return
   */
  public static String toRequestString(RequestMessage requestMessage, String format){
    if(StringUtils.equals(format, Constants.JSON)){
      return toRequestStringJson(requestMessage);
    }
    else{
      return toRequestStringXml(requestMessage);
    }
  }
  
  private static String toRequestStringXml(RequestMessage requestMessage) {
    StringBuffer request = new StringBuffer("<request>");
    request.append("<httpCommand>").append(requestMessage.getHttpCommand()).append("</httpCommand>");
    request.append("<httpCommandBody>").append(requestMessage.getHttpCommandBody()).append("</httpCommandBody>");
    request.append("<requestDate>").append(requestMessage.getRequestDate()).append("</requestDate>");
    request.append("<verifyCode>").append(requestMessage.getVerifyCode()).append("</verifyCode>");
    request.append("</request>");
    return request.toString();
  }

  private static String toRequestStringJson(RequestMessage requestMessage) {
    StringBuffer request = new StringBuffer("{");
    request.append("\"httpCommand\":\"").append(requestMessage.getHttpCommand()).append("\",");
    request.append("\"httpCommandBody\":").append(requestMessage.getHttpCommandBody()).append(",");
    request.append("\"requestDate\":\"").append(requestMessage.getRequestDate()).append("\",");
    request.append("\"verifyCode\":\"").append(requestMessage.getVerifyCode()).append("\"");
    request.append("}");
    return request.toString();
  }
  
  /**
   * 应答报文转为对象
   * @param response
   * @param format
   * @return
   * @throws IOException 
   * @throws JsonProcessingException 
   */
  public static ResponseMessage toResponseObj(String response, String format) throws JsonProcessingException, IOException{
    if(StringUtils.equals(format, Constants.JSON)){
      return toResponseObjJson(response);
    }
    else{
      return toResponseObjXml(response);
    }
  }
  
  private static ResponseMessage toResponseObjXml(String response) {
    XmlParser parser = new XmlParser(response);
    ResponseMessage msg = new ResponseMessage();
    msg.setRespCode(parser.getValue("respCode"));
    msg.setRespDesc(parser.getValue("respDesc"));
    msg.setRespDate(parser.getValue("respDate"));
    msg.setRespResult(parser.getValue("respResult"));
    Element ele = parser.getElement("respBody.content");
    if(ele != null) msg.setRespBody(ele.asXML());
    return msg;
  }

  private static ResponseMessage toResponseObjJson(String response) throws JsonProcessingException, IOException {
    ObjectMapper mapper = JsonMessageConventor.getInstacne().getObjectMapper();
    JsonNode rootNode = mapper.readTree(response);
    ResponseMessage msg = new ResponseMessage();
    msg.setRespCode(rootNode.path("respCode").getTextValue());
    msg.setRespDesc(rootNode.path("respDesc").getTextValue());
    msg.setRespDate(rootNode.path("respDate").getTextValue());
    msg.setRespResult(rootNode.path("respResult").getTextValue());
    msg.setRespBody(rootNode.path("respBody").toString());
    return msg;
  }

  /**
   * ����һ��HttpResponseMessage
   * @param body
   * @param isContent �Ƿ���Ҫ��һ��content
   * @return
   */
  public static ResponseMessage createSuccResponse(String body, boolean isContent){
    ResponseMessage response = new ResponseMessage();
    response.setRespCode("0");
    response.setRespDesc("");
    response.setRespResult("");
    response.setRespDate(DateFormatUtils.format(new Date(), Constants.DEFAULT_DATE_FORMAT));
    if(isContent) body = "<content>" + body + "</content>";              
    response.setRespBody(body);
    return response;
  }
  
  public static ResponseMessage createSuccResponse(String body){
    return createSuccResponse(body, false);
  }
  
  public static ResponseMessage createSuccResponse(){
    return createSuccResponse("");
  }
  
  /**
   * ���������HttpResponseMessage
   * @param res_code
   * @param res_desc
   * @param res_result
   * @return
   */
  public static ResponseMessage createFailResponse(String res_code, String res_desc, String res_result){
    ResponseMessage response = new ResponseMessage();
    response.setRespCode(res_code);
    response.setRespDesc(res_desc);
    response.setRespResult(res_result);
    response.setRespDate(DateFormatUtils.format(new Date(), Constants.DEFAULT_DATE_FORMAT));
    response.setRespBody("");
    return response;
  }
}
