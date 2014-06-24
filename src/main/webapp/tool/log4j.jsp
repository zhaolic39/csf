<%@page contentType="text/html;charset=UTF-8" language="java"%>
<%@page import="java.util.*"%>
<%@page import="org.apache.log4j.*"%>
<%@page import="org.apache.commons.lang.StringUtils"%>
<%@page isELIgnored="false"%>
<%!
String pattern = "%d{MM-dd HH:mm:ss,SSS} [%t] %-5p %l :%m%n";
Layout layout = new PatternLayout(pattern.toString());	//普通日志
//Layout layout = new HTMLLayout();	//HTML形式日志
//static String LOG_DIR = Contants.HTML_LOG_FOLDERNAME + "/";	//日志存放目录
static String LOG_DIR = "logs/"; //日志存放目录
String OUTPUT_FILE = "dynamic_log.log";
String MAX_FILE_SIZE = "10MB";
int MAX_BACKUP_INDEX = 10;
String DEFAULT_LEVEL = "INFO";
static final String APPENDER_NAME = "DYNAMIC_APPENDER"; //动态日志APPENDER名
static Integer appenderIndex = new Integer(0);

//设置所有的日志等级
private boolean setRootLoggerLevel(String level){
  Enumeration log_enum = LogManager.getCurrentLoggers();
  Logger logger = null;
  while (log_enum.hasMoreElements()) {
    logger = (Logger)log_enum.nextElement();
    logger.setLevel(Level.toLevel(level));
  }
  logger = Logger.getRootLogger();
  logger.setLevel(Level.toLevel(level));
  return true;
}

//设置日志等级,按模糊设置
public boolean setLoggerLevel(String name, String level){
  if (name == null) {
    return false;
  }
  if (name.equalsIgnoreCase("root")) {
    return setRootLoggerLevel(level);
  }
  Enumeration log_enum = LogManager.getCurrentLoggers();
  Logger logger = null;
  boolean bFind = false;

  while (log_enum.hasMoreElements()) {
    logger = (Logger)log_enum.nextElement();

    if (logger.getName().equals(name)) {
      bFind = true;
      logger.setLevel(Level.toLevel(level));
    }
    else if (logger.getName().startsWith(name)) {
      logger.setLevel(Level.toLevel(level));
    }
  }

  if (!(bFind)) {
  }
  return true;
}

//获得输出文件
public Appender getDefaultAppender(String sFile){
  Appender appender = null;
  if ((sFile == null) || (sFile.trim().length() == 0))
    sFile = OUTPUT_FILE;
  String LOG_PATH = this.getServletContext().getRealPath(LOG_DIR);	//获得物理绝对路径
  sFile = LOG_PATH + "/" + sFile;
  //sFile = LOG_DIR  + sFile;
  int i;
  synchronized (appenderIndex) {
    i = appenderIndex.intValue() + 1;
    appenderIndex = new Integer(i);
  }
  try {
    RollingFileAppender rollingAppender = new RollingFileAppender(layout, sFile, true);

    rollingAppender.setMaxBackupIndex(MAX_BACKUP_INDEX);
    rollingAppender.setMaxFileSize(MAX_FILE_SIZE);
    rollingAppender.setName(APPENDER_NAME);
    appender = rollingAppender;
  }
  catch (Exception ex) {
    ex.printStackTrace();
  }
  return appender;
}

//设置输出文件路径
public static boolean setLogAppender(String name, Appender appender){
  if (appender == null) {
    return false;
  }
  if (name == null) {
    return false;
  }
  if (name.equalsIgnoreCase("root")) {
    return setRootLoggerAppender(appender);
  }
  Enumeration log_enum = LogManager.getCurrentLoggers();
  Logger logger = null;
  boolean bFind = false;

  while (log_enum.hasMoreElements()) {
    logger = (Logger)log_enum.nextElement();
    if (logger.getName().equals(name)) {
      bFind = true;
      setAppender(logger, appender);
    }
    else if (logger.getName().startsWith(name)) {
      setAppender(logger, appender);
    }
  }

  if (!(bFind)) {
    //SimpleLogger simpleLogger = SimpleLogger.getLogger(name);
    //simpleLogger.setAppender(appender);
  }
  return true;
}

//设置根日志输出
private static boolean setRootLoggerAppender(Appender appender){
  Enumeration log_enum = LogManager.getCurrentLoggers();
  Logger logger = null;
  while (log_enum.hasMoreElements()) {
    logger = (Logger)log_enum.nextElement();
    setAppender(logger, appender);
  }

  logger = Logger.getRootLogger();
  setAppender(logger, appender);
  return true;
}

//设置appender
private static void setAppender(Logger logger, Appender appender){
//logger.removeAllAppenders();
  logger.removeAppender(APPENDER_NAME);
  logger.addAppender(appender);
  System.out.println("---" + logger.getName() + ":" + appender.getName());
  logger.setAdditivity(true);
}

//把动态设置的appender清除掉
private static void clearDynamicAppender(){
  Enumeration log_enum = LogManager.getCurrentLoggers();
  Logger logger = null;
  while (log_enum.hasMoreElements()) {
    logger = (Logger)log_enum.nextElement();
    logger.removeAppender(APPENDER_NAME);
  }
}

//查看日志设置
public static String[][] listLogger(String name) {
   Enumeration enumeration = LogManager.getCurrentLoggers();
   Vector vLogger = new Vector();
   Logger logger = null;
   String[] desc = null;
   Level level = null;
   Enumeration eAppender = null;
   //获取所有Logger的名称、级别、及输出文件
   //若该Logger未指定级别或输出文件（默认使用其父Logger的配置），则为null
   while (enumeration.hasMoreElements()) {
     logger = (Logger) enumeration.nextElement();
     if (name != null) { //过滤Logger名称
       if (!logger.getName().startsWith(name)) {
         continue;
       }
     }
     desc = new String[4];
     desc[0] = logger.getName();
     level = logger.getLevel();
     
     while(level==null){
       logger=(Logger)logger.getParent();
       level = logger.getLevel();
     }
     
     if (level != null) {
       desc[1] = level.toString();
     }
     
     
     eAppender = logger.getAllAppenders();
     
     while(eAppender==null||!eAppender.hasMoreElements()){
       logger=(Logger)logger.getParent();
       eAppender = logger.getAllAppenders();
     } 
          
     if (eAppender != null && eAppender.hasMoreElements()) {
       StringBuffer sbOut = new StringBuffer();
       StringBuffer sbName=new StringBuffer();
       Object appender = null;
       while (eAppender.hasMoreElements()) {
         appender = eAppender.nextElement();
         if (sbOut.length() > 0) {
           sbOut.append(",");
           //sbName.append(",");
         }
         if ( (ConsoleAppender.class).isInstance(appender)) {
           sbOut.append("CONSOLE");
           //sbName.append(((ConsoleAppender) appender).getName());
         }
         else if ( (FileAppender.class).isInstance(appender)) {
           String file_path = StringUtils.trimToEmpty(((FileAppender) appender).getFile()); //输出完整路径
           file_path = StringUtils.replace(file_path, "\\", "/");
           String file_name = StringUtils.substringAfterLast(file_path, "/");
           sbOut.append(file_path).append("");
           //sbName.append(((FileAppender) appender).getName());
           sbName.append(file_name);
         }
       }
       desc[2] = sbOut.toString();
       desc[3] = StringUtils.isEmpty(sbName.toString())?"":(sbName.toString());
     }
     else {
       desc[2] = "&nbsp;";
       desc[3] = "&nbsp;";
     }
     vLogger.add(desc);
   }
   
   //获取rootlogger的名称、级别、及输出文件
   logger = Logger.getRootLogger();
   desc = new String[4];
   desc[0] = logger.getName();
   level = logger.getLevel();
   if (level != null) {
     desc[1] = level.toString();
   }
   eAppender = logger.getAllAppenders();

   if (eAppender != null && eAppender.hasMoreElements()) {
     StringBuffer sbOut = new StringBuffer();
     StringBuffer sbName=new StringBuffer();
     Object appender = null;

     while (eAppender.hasMoreElements()) {
       appender = eAppender.nextElement();
       if (sbOut.length() > 0) {
         sbOut.append(",");
         //sbName.append(",");
       }
       if ( (ConsoleAppender.class).isInstance(appender)) {
         sbOut.append("CONSOLE");
         //sbName.append(((ConsoleAppender) appender).getName());
       }
       else if ( (FileAppender.class).isInstance(appender)) {
           String file_path = StringUtils.trimToEmpty(((FileAppender) appender).getFile()); //输出完整路径
           file_path = StringUtils.replace(file_path, "\\", "/");
           String file_name = StringUtils.substringAfterLast(file_path, "/");
           sbOut.append(file_path).append("");
           //sbName.append(((FileAppender) appender).getName());
           sbName.append(file_name);
       }
     }
     desc[2] = sbOut.toString();
     desc[3] = StringUtils.isEmpty(sbName.toString())?"":(LOG_DIR + sbName.toString());
   }
   else {
     desc[2] = "&nbsp;";
     desc[3] = "&nbsp;";
   }
   vLogger.add(desc);
   //将vector转为数组
   String[][] aaRet = null;
   if (vLogger != null && vLogger.size() > 0) {
     aaRet = new String[vLogger.size()][4];
     for (int i = 0; i < vLogger.size(); i++) {
       aaRet[i] = (String[]) vLogger.get(i);
     }
   }
   return aaRet;
}  
 %>
<html>
  <head>
    <title>log4j级别控制</title>
    <jsp:include flush="true" page="common_inc.jsp"></jsp:include>
  </head>
  <body>
    <!-- 修改日志级别 -->
    <%
      //获得日志级别
      String logName = StringUtils.trimToEmpty(request.getParameter("log"));		//日志名
      String log_level = StringUtils.trimToEmpty(request.getParameter("level"));	//日志等级
      String log_output = StringUtils.trimToEmpty(request.getParameter("log_output"));	//日志输出文件
      String log_act = StringUtils.trimToEmpty(request.getParameter("log_act"));	//日志操作
      
      if (("setlog".equals(log_act) || "add".equals(log_act)) && !StringUtils.isEmpty(logName)) {
          //如果logName为空字符串,则设置根日志为配置的级别,否则设置[logName]所表示的日志为配置的日志级别
          //Logger log = ("".equals(logName) ? Logger.getRootLogger() : Logger.getLogger(logName.trim()));
          //设置日志级别
          //log.setLevel(Level.toLevel(request.getParameter("level"), Level.DEBUG));
          setLoggerLevel(logName, log_level);
          if("add".equals(log_act)){
            if(!StringUtils.isEmpty(log_output)){
              setLogAppender(logName, getDefaultAppender(log_output));  
            }
            else{
              clearDynamicAppender(); //把动态设置的appender清除掉
            }
          }
      }
      
      String[][] aaLogger = listLogger(logName);
   %>

    <!-- 显示 -->
    <form  name="logForm" action="log4j.jsp" method="post">
      <table width="100%" class="table-form">
        <tr>
          <td class="td_label">日志名：</td>
          <td class="td_input" >
            <input type="text" name="log" value="<%=logName%>"/>
          </td>
          <td class="td_label">日志级别：</td>
          <td class="td_input">
            <select name="level">
              <option value="DEBUG">DEBUG</option>
              <option value="INFO">INFO</option>
              <option value="WARN">WARN</option>
              <option value="ERROR">ERROR</option>
            </select>
            <input type="hidden" name="url" value="log/log4j"/>
            <input type="hidden" name="log_act" value="query"/>
          </td>
          <td class="td_label">输出文件：</td>
          <td class="td_input">
            <input type="text" name="log_output" value="<%=log_output%>"/>
          </td>
        </tr>

        <tr>
          <td colspan="6"  align="center">
            <input type="button" value="查询" id="queryBtn" class="button" onclick="queryLog()"/>&nbsp;&nbsp;
            <input type="button" value="设置日志等级" id="addBtn" class="button" onclick="setLog()">&nbsp;&nbsp;
            <input type="button" value="设置日志与输出文件" id="addBtn" class="button" onclick="addLog()">&nbsp;&nbsp;
            <input type="reset" value="重  置" id="resetBtn" class="button" />
          </td>
        </tr>
      </table>
    </form>

    <!-- 日志控制工具条 -->
    <div id="gridToolsBar" style="padding-bottom: 0px;"> </div>

    <!-- 显示日志 -->
    <form>
      <table width="100%" class="list_data">
        <thead>
        <tr  >
          <td >日志信息</td>
          <td >日志级别</td>
          <td >输出文件</td>
          <td  style="display:none">设置新的日志级别</td>
        </tr>
        </thead>
        <%for(int i=0;i<aaLogger.length;i++){
          String[] loggers = aaLogger[i];
        %>
          <tr class="<%=i%2==0?"tr_even":"tr_odd" %>">
            <td ><%=StringUtils.trimToEmpty(loggers[0]) %></td>
            <td ><%=StringUtils.trimToEmpty(loggers[1]) %></td>
            <td >
              <a target="_blank" href="openlog.jsp?log_file_name=<%=StringUtils.trimToEmpty(loggers[3])%>"><%=StringUtils.trimToEmpty(loggers[2]) %></a>
            </td>
            <td  style="display:none"><%=StringUtils.trimToEmpty(loggers[3])%></td>
          </tr>
        <%}%>
      </table>
    </form>

  <!-- js -->
  <script type="text/javascript">

    //初始化工具条
    initToolBar();

    /**
     *显示所有日志
     */
    function showAllLog() {
       window.location.href = "<%=request.getContextPath()%>/urlDispatch.spr?url=log/log4j&showAll=true";
    }

    /**
     *显示配置的日志
     */
     function showConfigLog() {
        window.location.href = "<%=request.getContextPath()%>/urlDispatch.spr?url=log/log4j";
     }


     /**
      *功能描述:
      *   初始化工具条
      */
     function initToolBar() {
     }
     
     //增加日志设置
     function addLog(){
       logForm.log_act.value = "add";
       logForm.submit();
     }
     
     function queryLog(){
       logForm.log_act.value = "query";
       logForm.submit();
     }

      //只设置日志等级
     function setLog(){
       logForm.log_act.value = "setlog";
       logForm.submit();
     }
</script>
  </body>
</html>
