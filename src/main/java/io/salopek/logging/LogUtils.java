package io.salopek.logging;

import io.salopek.constant.LogKeys;
import io.salopek.processor.GameProcessorImpl;
import org.apache.commons.lang3.time.StopWatch;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class LogUtils {
  private static final Logger LOGGER = LoggerFactory.getLogger(LogUtils.class);

  private LogUtils() {
  }

  private static final String SPACE = " ";
  private static final String ENTRY = "[ENTRY]";
  private static final String EXIT = "[EXIT]";
  private static final String DELIMITER = "=";
  private static final String METHOD = "method";
  private static final String MS = "ms";
  private static final String EXEC_TIME = "executionTime";

  public static StopWatch stopWatch() {
    StopWatch sw = new StopWatch();
    sw.start();
    return sw;
  }

  public static String methodEntry(String methodName) {
    return methodLog(ENTRY, methodName);
  }

  public static String methodExit(String methodName) {
    return methodLog(EXIT, methodName);
  }

  public static String methodExit(String methodName, StopWatch sw) {
    return append(methodLog(EXIT, methodName), SPACE, logExecutionTime(sw));
  }

  public static String logObject(String key, Object value) {
    if (null == key || key.isEmpty()) {
      return "";
    } else {
      return append(key, DELIMITER, (null == value ? "null" : replaceNewLine(value.toString())));
    }
  }

  public static <E extends Exception> void logException(E exception) {
    LogBuilder lb = LogBuilder.get().log("Exception occurred").kv(LogKeys.MESSAGE, exception.getMessage());
    LOGGER.error(lb.build());
  }

  private static String methodLog(String prefix, String methodName) {
    return append(prefix, SPACE, METHOD, DELIMITER, methodName);
  }

  private static String replaceNewLine(String s) {
    return s;
  }

  private static String logExecutionTime(StopWatch sw) {
    return logObject(EXEC_TIME, sw.getTime() + MS);
  }

  private static String append(String... strings) {
    StringBuilder sb = new StringBuilder();
    for (String s : strings) {
      sb.append(s);
    }
    return sb.toString();
  }
}