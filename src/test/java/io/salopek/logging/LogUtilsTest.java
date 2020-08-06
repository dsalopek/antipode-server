package io.salopek.logging;

import io.salopek.model.Point;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class LogUtilsTest {

  @Test
  void methodEntry() {
    String methodName = "method1";

    assertThat(LogUtils.methodEntry(methodName)).isEqualTo("[ENTRY] method=method1");
  }

  @Test
  void methodExit() {
    String methodName = "method1";

    assertThat(LogUtils.methodExit(methodName)).isEqualTo("[EXIT] method=method1");
  }

  @Test
  void methodExit_WithDuration() {
    String methodName = "method1";
    long duration = 1234L;

    assertThat(LogUtils.methodExit(methodName, duration)).isEqualTo("[EXIT] method=method1 executionTime=1234ms");
  }

  @Test
  void logObject_String() {
    String key = "key";
    String value = "value";

    assertThat(LogUtils.logObject(key, value)).isEqualTo("key=value");
  }

  @Test
  void logObject_Point() {
    String key = "point";
    Point value = new Point(100, 55);

    assertThat(LogUtils.logObject(key, value)).isEqualTo("point=Point{longitude=100.0, latitude=55.0}");
  }

  @Test
  void logObject_NullValue() {
    String key = "key";
    Object value = null;

    assertThat(LogUtils.logObject(key, value)).isEqualTo("key=null");
  }

  @Test
  void logObject_NullKey() {
    String key = "";
    Object value = null;

    assertThat(LogUtils.logObject(key, value)).isEqualTo("");

    key = null;
    value = null;

    assertThat(LogUtils.logObject(key, value)).isEqualTo("");
  }

}
