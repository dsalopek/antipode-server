package io.salopek.model;

import io.salopek.util.PointUtils;

public class Point {
  private double longitude;
  private double latitude;

  public Point() {
  }

  public Point(double longitude, double latitude) {
    setLongitude(longitude);
    setLatitude(latitude);
  }

  public double getLongitude() {
    return longitude;
  }

  public void setLongitude(double longitude) {
    PointUtils.validateLongitude(longitude);
    this.longitude = longitude;
  }

  public double getLatitude() {
    return latitude;
  }

  public void setLatitude(double latitude) {
    PointUtils.validateLatitude(latitude);
    this.latitude = latitude;
  }

  @Override
  public String toString() {
    return "Point{" +
      "longitude=" + longitude +
      ", latitude=" + latitude +
      '}';
  }
}