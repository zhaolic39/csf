package net.csf.utils;

import java.text.ParseException;
import java.util.Date;

import org.apache.commons.lang.time.DateUtils;

/**
 * <p>Title: �����ַ����࣬��apache�����·�װ</p>
 * <p>Description:</p>
 *
 * @author zhaoli
 * @version 1.0 2012-1-6
 */
public class DateFormatUtils {
  
  /**
   * �ַ�תdate��
   * @param datestr
   * @param pattern
   * @return
   */
  public static Date parse(String datestr, String pattern){
    Date d = null;
    try {
      d = DateUtils.parseDate(datestr, new String[]{pattern});
    } catch (ParseException e) {
      e.printStackTrace();
    }
    return d;
  }
  
  /**
   * �ַ�תdate�ͣ�֧�ֶ��ָ�ʽ
   * @param datestr
   * @param patterns
   * @return
   */
  public static Date parse(String datestr, String[] patterns){
    Date d = null;
    try {
      d = DateUtils.parseDate(datestr, patterns);
    } catch (ParseException e) {
      e.printStackTrace();
    }
    return d;
  }
  
  /**
   * date��ת�ַ�
   * @param d
   * @param pattern
   * @return
   */
  public static String format(Date d, String pattern){
    return org.apache.commons.lang.time.DateFormatUtils.format(d, pattern);
  }
  
  /**
   * date��ת��׼�ַ�
   * @param d
   * @return
   */
  public static String formatDefaultDate(Date d){
    return format(d, Constants.DEFAULT_DATE_FORMAT);
  }
  
  /**
   * date��ת��ʾ�ַ�
   * @param d
   * @return
   */
  public static String formatShowDate(Date d){
    return format(d, Constants.SHOW_DATE_FORMAT);
  }
}
