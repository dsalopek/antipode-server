package io.salopek.util;

import io.salopek.logging.LogUtils;
import io.salopek.model.Point;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import static io.salopek.constant.AntipodeConstants.EXC_LATITUDE_INVALID;
import static io.salopek.constant.AntipodeConstants.EXC_LONGITUDE_INVALID;
import static io.salopek.constant.AntipodeConstants.EXC_ORIGIN_REQUIRED;
import static io.salopek.constant.AntipodeConstants.LATITUDE;
import static io.salopek.constant.AntipodeConstants.LONGITUDE;

public class PointUtils {

  private PointUtils() {
  }

  private static final Logger LOGGER = LoggerFactory.getLogger(PointUtils.class);

  public static Point getRandomOrigin() {
    double latitude = RandomUtils.getRandomLatitude();
    double longitude = RandomUtils.getRandomLongitude();
    Point randomOrigin = new Point(longitude, latitude);

    LOGGER.info(LogUtils.logObject("randomOrigin", randomOrigin));
    return randomOrigin;
  }

  public static Point calculateAntipode(Point origin) {
    if (null == origin) {
      LOGGER.error("Origin is null");
      throw new IllegalArgumentException(EXC_ORIGIN_REQUIRED);
    }

    double oppX = getOppositeXValue(origin.getLongitude());
    double oppY = getOppositeYValue(origin.getLatitude());

    Point antipode = new Point(oppX, oppY);

    LOGGER.info(LogUtils.logObject("antipode", antipode));
    return antipode;
  }

  public static void validateLongitude(double longitude) {
    if (LONGITUDE < Math.abs(longitude)) {
      throw new IllegalArgumentException(EXC_LONGITUDE_INVALID);
    }
  }

  public static void validateLatitude(double latitude) {
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
