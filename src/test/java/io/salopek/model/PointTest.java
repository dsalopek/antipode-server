package io.salopek.model;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertThrows;

class PointTest {

  @Test
  @DisplayName("Point with valid input")
  void point_validInput() {

    assertDoesNotThrow(() -> new Point(169.345, 85.241343));
    assertDoesNotThrow(() -> new Point(180, 90));
    assertDoesNotThrow(() -> new Point(-180, -90));

  }

  @Test
  @DisplayName("Point with invalid input")
  void point_invalidInput() {

    assertThrows(IllegalArgumentException.class, () -> new Point(182, 85.241343));
    assertThrows(IllegalArgumentException.class, () -> new Point(-182, 55.234534));
    assertThrows(IllegalArgumentException.class, () -> new Point(160, 95));
    assertThrows(IllegalArgumentException.class, () -> new Point(-160, -95));

  }
}