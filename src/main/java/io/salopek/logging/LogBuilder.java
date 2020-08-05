package io.salopek.logging;

import io.salopek.util.LogUtils;

import java.util.ArrayList;
import java.util.List;

public class LogBuilder {

  private final List<String> valueList = new ArrayList<>();

  private LogBuilder() {
  }

  public static LogBuilder get() {
    return new LogBuilder();
  }

  public LogBuilder log(String s) {
    append(s);
    return this;
  }

  public LogBuilder kv(String key, Object value) {
    append(LogUtils.logObject(key, value));
    return this;
  }

  public String build() {
    StringBuilder sb = new StringBuilder();
    for (String val : valueList) {
      sb.append(val);
      sb.append(" ");
    }

    valueList.clear();

    return sb.toString().trim();
  }

  private void append(String s) {
    if (s == null || s.isEmpty()) {
      return;
    }
    valueList.add(s);
  }
}