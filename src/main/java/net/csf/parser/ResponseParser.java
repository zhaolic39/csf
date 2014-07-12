package net.csf.parser;

import java.io.IOException;

import net.csf.controller.ServiceContext;

/**
 * 请求响应处理器
 * @author zhaolic39
 */
public interface ResponseParser {
  public void parser(ServiceContext context) throws IOException;
}
