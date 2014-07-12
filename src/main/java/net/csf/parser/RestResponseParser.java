package net.csf.parser;

import java.io.IOException;

import net.csf.controller.ServiceContext;
import net.csf.utils.MessageUtils;
import net.csf.utils.NetWorkUtils;

public class RestResponseParser implements ResponseParser {

  public void parser(ServiceContext context) throws IOException {
    String resStr = "";
    if(context.getResultCode() == 0){
      
    }
    else{ //异常处理
      resStr = MessageUtils.toResponseXml(context.getResultCode(), context.getError(), "");
    }
    NetWorkUtils.write(context.getResponse(), resStr);
  }
}
