package io.salopek.util;

import io.salopek.constant.AntipodeConstants;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

public class RandomUtilsTest {
  @Test
  void validRandomLongitude() {
    for (int i = 0; i < 25; i++) {
      assertThat(RandomUtils.getRandomLongitude()).isBetween(-AntipodeConstants.LONGITUDE, AntipodeConstants.LONGITUDE);
    }
  }

  @Test
  void validRandomLatitude() {
    for (int i = 0; i < 25; i++) {
      assertThat(RandomUtils.getRandomLatitude()).isBetween(-AntipodeConstants.LATITUDE, AntipodeConstants.LATITUDE);
    }
  }
}
