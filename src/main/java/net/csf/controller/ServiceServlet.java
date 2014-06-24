package net.csf.controller;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.csf.exception.BusinessException;
import net.csf.request.RequestMessage;
import net.csf.response.ResponseMessage;
import net.csf.utils.Constants;
import net.csf.utils.MessageUtils;
import net.csf.utils.MultiFormData;
import net.csf.utils.ServiceUtils;
import net.csf.utils.XmlFormatUtils;

import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.servlet.ServletFileUpload;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**
 * <p>Title: 请求响应servlet </p>
 * <p>Description:</p>
 *
 * @author zhaoli
 * @version 1.0 2013-1-31
 */
public class ServiceServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	private static Log logger = LogFactory.getLog(ServiceServlet.class);

	public void doGet(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
	  String request = "";
	  String format = "";  //报文格式
	  RequestMessage reqMessage = null;
	  ResponseMessage response = null;
	  FileItem fileItem = null;
	  try {
	    if(ServletFileUpload.isMultipartContent(req)){ //带附件请求
        MultiFormData formdata = new MultiFormData(req);
        request = formdata.getParameter(Constants.REQUEST);
        format = formdata.getParameter(Constants.FORMAT);
        fileItem = formdata.getUploadFile(Constants.FILE_PART);  //附件
  	  }else{ //普通请求
  	    request = req.getParameter(Constants.REQUEST);
  	    format = req.getParameter(Constants.FORMAT);
  	  }
	    if (logger.isDebugEnabled()) {
	      logger.debug("request:\n" + XmlFormatUtils.formatXmlStr(request));
	    }
	    if (!StringUtils.isEmpty(request)) {
	      reqMessage = MessageUtils.toRequestMessage(request, format);
        reqMessage.setFileItem(fileItem);
        
        response = ServiceUtils.callService(reqMessage, format);
	    } else {
	      response = MessageUtils.createFailResponse("-1", "no request info", "no request info");
	    }
    } 
	  catch (BusinessException e) {
      response = MessageUtils.createFailResponse(e.getErrorCode(), e.getErrorDesc(), e.getErrorResult());
    } 
	  catch (Exception e) {
      logger.error("http command error", e);
      response = MessageUtils.createFailResponse("1", e.toString(), e.toString());
    }
		
	  String resp = MessageUtils.toResponseMessage(response, format);
		if(logger.isDebugEnabled()){
		  logger.debug("response:\n" + resp);
		}
    res.setCharacterEncoding("UTF-8");
    res.setContentType("text/plain; charset=UTF-8");
    res.getWriter().print(resp);
	}

	public void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		doGet(req, resp);
	}
}
