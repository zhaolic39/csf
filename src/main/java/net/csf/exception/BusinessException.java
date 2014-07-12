package net.csf.exception;

/**
 * <p>Title: 业务异常 </p>
 * <p>Description:</p>
 *
 * @author zhaoli
 * @version 1.0 2013-1-31
 */
public class BusinessException extends Exception {
  private static final long serialVersionUID = 1L;

  private String errorDesc;
  private int errorCode = 0;

  /**
   * 业务异常构造方法
   * @param code
   * @param desc
   * @param result
   */
  public BusinessException(int code, String desc, String result) {
    errorCode = code;
    errorDesc = desc;
  }

  public BusinessException() {
    super();
  }

  /**
   * 
   * @param message
   */
  public BusinessException(String message) {
    super(message);
  }

  /**
   * 
   * @param message
   * @param cause
   */
  public BusinessException(String message, Throwable cause) {
    super(message, cause);
  }

  /**
   * 
   * @param cause
   */
  public BusinessException(Throwable cause) {
    super(cause);
  }

  public String getErrorDesc() {
    return errorDesc;
  }

  public void setErrorDesc(String errorDesc) {
    this.errorDesc = errorDesc;
  }

  public int getErrorCode() {
    return errorCode;
  }

  public void setErrorCode(int errorCode) {
    this.errorCode = errorCode;
  }

}
