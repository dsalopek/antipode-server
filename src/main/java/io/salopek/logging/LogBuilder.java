package io.salopek.logging;

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

  public <C extends Enum<C>> LogBuilder kv(Enum<C> e, Object value) {
    kv(e.toString(), value);
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
    if (null != s && !s.isEmpty()) {
      valueList.add(s);
    }
  }
}