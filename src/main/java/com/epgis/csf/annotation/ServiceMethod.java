package com.epgis.csf.annotation;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * <p>Title: HTTP服务方法 </p>
 * <p>Description:</p>
 *
 * @author zhaoli
 * @version 1.0 2012-11-28
 */
@Target({ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface ServiceMethod {
  /**
   * HTTP命令名
   * @return
   */
  String command();
  
  /**
   * 描述
   * @return
   */
  String desc() default "";
}
