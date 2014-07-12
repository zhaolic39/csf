package net.csf.parser;

import net.csf.controller.ServiceContext;
import net.csf.request.IRequestParam;

/**
 * http servlet请求转换器
 * @author zhaolic39
 */
public interface RequestParser {
  public IRequestParam parser(ServiceContext context);
}
