package io.salopek.util;

import io.salopek.constant.AntipodeConstants;
import io.salopek.model.Point;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertThrows;

class PointUtilsTest {

  @Test
  void getRandomOrigin() {
    Point actualOrigin = PointUtils.getRandomOrigin();

    assertThat(actualOrigin).isNotNull();
  }

  @Test
  void calculateAntipode_success() {
    final Point origin = new Point(-97.687261, 30.369852);
    final Point expectedAntipode = new Point(82.312739, -30.369852);

    assertThat(PointUtils.calculateAntipode(origin)).usingRecursiveComparison().isEqualTo(expectedAntipode);
  }

  @Test
  void calculateAntipode_successNegLong() {
    final Point origin = new Point(97.687261, 30.369852);
    final Point expectedAntipode = new Point(-82.312739, -30.369852);

    assertThat(PointUtils.calculateAntipode(origin)).usingRecursiveComparison().isEqualTo(expectedAntipode);
  }

  @Test
  void calculateAntipode_nullOrigin() {
    final Point origin = null;

    assertThrows(IllegalArgumentException.class, () -> PointUtils.calculateAntipode(origin));
  }

  @Test
  void validateLongitude_valid() {
    double longitude = AntipodeConstants.LONGITUDE;

    assertDoesNotThrow(() -> PointUtils.validateLongitude(longitude));
  }

  @Test
  void validateLongitude_invalid() {
    double longitude = -189d;

    assertThrows(IllegalArgumentException.class, () -> PointUtils.validateLongitude(longitude));
  }

  @Test
  void validateLatitude_valid() {
    double latitude = AntipodeConstants.LATITUDE;

    assertDoesNotThrow(() -> PointUtils.validateLatitude(latitude));
  }

  @Test
  void validateLatitude_invalid() {
    double latitude = -95d;

    assertThrows(IllegalArgumentException.class, () -> PointUtils.validateLatitude(latitude));
  }
}