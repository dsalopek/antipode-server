package io.salopek.logging;

import io.salopek.model.Point;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class LogBuilderTest {
  @Test
  void logBuilderTest() {
    LogBuilder lb = LogBuilder.get().log("Enter LB").kv("Key1", new Point(5, 15)).kv("Key2", new Point(7, 2));
    String lbOutput = lb.build();

    String expectedOutput = "Enter LB Key1=Point{longitude=5.0, latitude=15.0} Key2=Point{longitude=7.0, latitude=2.0}";
    assertThat(lbOutput).isEqualTo(expectedOutput);
  }

  @Test
  void logBuilderTest_NullAppend() {
    LogBuilder lb = LogBuilder.get().log("").kv("", null).kv("", null);
    String lbOutput = lb.build();

    String expectedOutput = "";
    assertThat(lbOutput).isEqualTo(expectedOutput);
  }

  @Test
  void logBuilderTest_NullValueAppend() {
    LogBuilder lb = LogBuilder.get().kv("test", null).kv("test1", "");
    String lbOutput = lb.build();

    String expectedOutput = "test=null test1=";
    assertThat(lbOutput).isEqualTo(expectedOutput);
  }
}