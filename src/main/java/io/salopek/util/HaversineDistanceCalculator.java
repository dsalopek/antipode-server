package io.salopek.util;

import io.salopek.model.Point;

import java.text.DecimalFormat;

public class HaversineDistanceCalculator implements DistanceCalculator {

  //  Haversine
  //  formula:	a = sin²(Δφ/2) + cos φ1 ⋅ cos φ2 ⋅ sin²(Δλ/2)
  //  c = 2 ⋅ atan2( √a, √(1−a) )
  //  d = R ⋅ c
  //  where	φ is latitude, λ is longitude, R is earth’s radius (mean radius = 6,371km);
  //  note that angles need to be in radians to pass to trig functions!
  // https://www.movable-type.co.uk/scripts/latlong.html

  private static final double earthRadiusFeet = 20902e3;

  @Override
  public double getDistance(Point pointA, Point pointB) {

    double lat1 = pointA.getLatitude();
    double lat2 = pointB.getLatitude();
    double lon1 = pointA.getLongitude();
    double lon2 = pointB.getLongitude();

    double theta1 = lat1 * Math.PI / 180d;
    double theta2 = lat2 * Math.PI / 180d;

    double deltaTheta = (lat2 - lat1) * Math.PI / 180d;
    double deltaLambda = (lon2 - lon1) * Math.PI / 180d;

    double a = Math.sin(deltaTheta / 2) * Math.sin(deltaTheta / 2) + Math.cos(theta1) * Math.cos(theta2) * Math
      .sin(deltaLambda / 2) * Math.sin(deltaLambda / 2);
    double c = 2 * Math.atan2(Math.sqrt(a), Math.sqrt(1 - a));

    return round(earthRadiusFeet * c);
  }

  private double round(double input) {
    DecimalFormat df = new DecimalFormat("#.##");
    return Double.parseDouble(df.format(input));
  }
}
