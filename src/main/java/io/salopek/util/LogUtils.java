package io.salopek.util;

public class LogUtils {

  private static final String PARENS = "()";
  private static final String SPACE = " ";
  private static final String ENTRY = "[ENTRY]";
  private static final String EXIT = "[EXIT]";
  private static final String DELIMITER = "=";
  private static final String METHOD = "method";

  public static String methodEntry(String methodName) {
    return methodLog(ENTRY, methodName);
  }

  public static String methodExit(String methodName) {
    return methodLog(EXIT, methodName);
  }

  public static String logObject(String key, Object value) {
    return key + DELIMITER + (null == value ? "null" : replaceNewLine(value.toString()));
  }

  private static String methodLog(String prefix, String methodName) {
    return prefix + SPACE + METHOD + DELIMITER + methodName;
  }

  private static String replaceNewLine(String s) {
    //    return s.replaceAll("[\\n+]", "");
    return s;
  }
}