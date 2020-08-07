package io.salopek.logging;

public class LogUtils {

  private LogUtils() {
  }

  private static final String SPACE = " ";
  private static final String ENTRY = "[ENTRY]";
  private static final String EXIT = "[EXIT]";
  private static final String DELIMITER = "=";
  private static final String METHOD = "method";
  private static final String MS = "ms";
  private static final String EXEC_TIME = "executionTime";

  public static String methodEntry(String methodName) {
    return methodLog(ENTRY, methodName);
  }

  public static String methodExit(String methodName) {
    return methodLog(EXIT, methodName);
  }

  public static String methodExit(String methodName, long executionTime) {
    return append(methodLog(EXIT, methodName), SPACE, logExecutionTime(executionTime));
  }

  public static String logObject(String key, Object value) {
    if (null == key || key.isEmpty()) {
      return "";
    } else {
      return append(key, DELIMITER, (null == value ? "null" : replaceNewLine(value.toString())));
    }
  }

  private static String methodLog(String prefix, String methodName) {
    return append(prefix, SPACE, METHOD, DELIMITER, methodName);
  }

  private static String replaceNewLine(String s) {
    return s;
  }

  private static String logExecutionTime(long duration) {
    return logObject(EXEC_TIME, duration + MS);
  }

  private static String append(String... strings) {
    StringBuilder sb = new StringBuilder();
    for (String s : strings) {
      sb.append(s);
    }
    return sb.toString();
  }
}