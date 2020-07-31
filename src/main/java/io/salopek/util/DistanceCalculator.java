package io.salopek.util;

import io.salopek.model.Point;

public interface DistanceCalculator {
  double getDistance(Point pointA, Point pointB);
}
