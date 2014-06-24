package com.epgis.csf.annotation;
import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Inherited;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
/**
 * 用于定义同步命令的处理 
 * @date 2011 Apr 29, 2011 5:25:01 PM
 */
@Documented
@Target({ElementType.TYPE}) 
@Retention(RetentionPolicy.RUNTIME)
@Inherited
public @interface HttpHandler {
  /**
   * HTTP命令名
   * @return
   */
    String command();
    
    /**
     * 服务描述
     * @return
     */
    String desc() default "";
}