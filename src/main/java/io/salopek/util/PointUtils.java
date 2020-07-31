package io.salopek.util;

import io.salopek.model.Point;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import static io.salopek.constant.AntipodeConstants.EXC_LATITUDE_INVALID;
import static io.salopek.constant.AntipodeConstants.EXC_LONGITUDE_INVALID;
import static io.salopek.constant.AntipodeConstants.EXC_ORIGIN_REQUIRED;
import static io.salopek.constant.AntipodeConstants.LATITUDE;
import static io.salopek.constant.AntipodeConstants.LONGITUDE;

public class PointUtils {

  private static final Logger LOGGER = LoggerFactory.getLogger(PointUtils.class);

  public static Point getRandomOrigin() throws IllegalArgumentException {
    LOGGER.info(LogUtils.methodEntry("getRandomOrigin"));

    double latitude = RandomUtils.getRandomLatitude();
    double longitude = RandomUtils.getRandomLongitude();
    Point randomOrigin = new Point(longitude, latitude);

    LOGGER.info(LogUtils.logObject("randomOrigin", randomOrigin));
    LOGGER.info(LogUtils.methodExit("getRandomOrigin"));
    return randomOrigin;
  }

  public static Point calculateAntipode(Point origin) throws IllegalArgumentException {
    LOGGER.info(LogUtils.methodEntry("calculateAntipode"));
    LOGGER.info(LogUtils.logObject("origin", origin));

    if (null == origin) {
      LOGGER.error("Origin is null");
      throw new IllegalArgumentException(EXC_ORIGIN_REQUIRED);
    }

    double oppX = getOppositeXValue(origin.getLongitude());
    double oppY = getOppositeYValue(origin.getLatitude());

    Point antipode = new Point(oppX, oppY);

    LOGGER.info(LogUtils.logObject("antipode", antipode));
    LOGGER.info(LogUtils.methodExit("calculateAntipode"));
    return antipode;
  }

  public static void validateLongitude(double longitude) throws IllegalArgumentException {
    if (LONGITUDE < Math.abs(longitude)) {
      throw new IllegalArgumentException(EXC_LONGITUDE_INVALID);
    }
  }

  public static void validateLatitude(double latitude) throws IllegalArgumentException {
    if (LATITUDE < Math.abs(latitude)) {
      throw new IllegalArgumentException(EXC_LATITUDE_INVALID);
    }
  }

  private static double getOppositeXValue(double x) {
    if (x < 0) {
      return x + LONGITUDE;
    } else {
      return x - LONGITUDE;
    }
  }

  private static double getOppositeYValue(double y) {
    return y * -1;
  }
}
