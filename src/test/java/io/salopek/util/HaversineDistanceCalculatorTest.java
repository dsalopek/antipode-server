package io.salopek.util;

import io.salopek.model.Point;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class HaversineDistanceCalculatorTest {

  @Test
  void haversineDistanceTest() {
    Point pointA = new Point(-21.8174, 64.1265);
    Point pointB = new Point(-74.0060, 40.7128);

    HaversineDistanceCalculator distanceCalculator = new HaversineDistanceCalculator();

    assertThat(distanceCalculator.getDistance(pointA, pointB)).isEqualTo(1.380627381e7);
  }

  @Test
  void haversineDistanceTest_0samePoints() {
    Point pointA = new Point(0, 0);

    HaversineDistanceCalculator distanceCalculator = new HaversineDistanceCalculator();

    assertThat(distanceCalculator.getDistance(pointA, pointA)).isEqualTo(0);
  }

}