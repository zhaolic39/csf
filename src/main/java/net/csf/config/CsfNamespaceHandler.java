package net.csf.config;

import org.springframework.beans.factory.xml.NamespaceHandlerSupport;

/**
 * <p>Title: CSF配置解析器 </p>
 * <p>Description:</p>
 *
 * @author zhaoli
 * @version 1.0 2013-10-24
 */
public class CsfNamespaceHandler extends NamespaceHandlerSupport {

    public void init() {
        registerBeanDefinitionParser("csf-remote", CsfRemoteBeanDefinitionParser.getInstance());
    }
}

