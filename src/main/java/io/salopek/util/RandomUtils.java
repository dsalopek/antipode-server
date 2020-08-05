package io.salopek.util;

import java.util.Random;

import static io.salopek.constant.AntipodeConstants.LATITUDE;
import static io.salopek.constant.AntipodeConstants.LONGITUDE;

public class RandomUtils {

  private RandomUtils() {
  }

  private static final Random random = new Random();

  public static double getRandomLatitude() {
    return getRandomDouble(LATITUDE);
  }

  public static double getRandomLongitude() {
    return getRandomDouble(LONGITUDE);
  }

  private static double getRandomDouble(double bound) {
    return (-1 * bound) + (bound + bound) * random.nextDouble();
  }
}
