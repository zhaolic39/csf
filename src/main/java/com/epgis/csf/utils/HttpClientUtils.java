package com.epgis.csf.utils;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Map;

import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.HttpException;
import org.apache.commons.httpclient.methods.PostMethod;
import org.apache.commons.httpclient.params.HttpMethodParams;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;


/**
 * <p>Title: HTTP请求工具类 </p>
 * <p>Description:</p>
 *
 * @author zhaoli
 * @version 1.0 2013-10-24
 */
public class HttpClientUtils {
  private static Log logger = (Log) LogFactory.getLog(HttpClientUtils.class);
  
  /**
   * HTTP POST请求
   * @param url
   * @param param
   * @return
   * @throws HttpException
   * @throws IOException
   */
  public static String doPost(String url, Map<String, String> param) throws HttpException, IOException{
    PostMethod postMethod = new PostMethod(url);
    HttpClient httpClient = new HttpClient();
    
    if(param != null){
      for(Map.Entry<String, String> e:param.entrySet()){
        postMethod.addParameter(e.getKey(), e.getValue());
      }
    }
    
    try{
      postMethod.getParams().setParameter(HttpMethodParams.HTTP_CONTENT_CHARSET, "UTF-8");
      int statusCode = httpClient.executeMethod(postMethod);
      if(statusCode == 200 && !"0".equals(statusCode)){
        BufferedReader br = new BufferedReader(new InputStreamReader(postMethod.getResponseBodyAsStream(), Constants.DEFAULT_CHARSET));
        StringBuffer res = new StringBuffer();
        String resTemp = "";
        while((resTemp = br.readLine()) != null){
          res.append(resTemp);
        }
        return res.toString();
      }
      else{
        throw new HttpException("HTTP请求出错, statusCode:" + statusCode);
      }
    }
    catch(HttpException e){
      throw e;
    }
    catch(IOException e){
      throw e;
    }
    finally{
      postMethod.releaseConnection();
      httpClient.getHttpConnectionManager().closeIdleConnections(0);
    }
  }
}
