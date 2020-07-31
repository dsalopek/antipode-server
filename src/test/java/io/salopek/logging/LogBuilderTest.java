package io.salopek.logging;

import io.salopek.model.Point;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class LogBuilderTest {
  @Test
  void logBuilderTest() {
    LogBuilder lb = LogBuilder.get();
    lb = lb.kv("Key1", new Point(5, 15)).kv("Key2", new Point(7, 2));

    System.out.println(lb.build());

    System.out.println();
  }
}