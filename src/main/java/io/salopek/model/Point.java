package io.salopek.model;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import io.salopek.util.PointUtils;

import javax.validation.constraints.NotEmpty;

public class Point {
  @NotEmpty(message = "longitude must not be empty!")
  private double longitude;

  @NotEmpty(message = "latitude must not be empty!")
  private double latitude;

  public Point() {
    this(0, 0);
  }

  @JsonCreator
  public Point(@JsonProperty("longitude") double longitude, @JsonProperty("latitude") double latitude) {
    setLongitude(longitude);
    setLatitude(latitude);
  }

  @JsonProperty("longitude")
  public double getLongitude() {
    return longitude;
  }

  public void setLongitude(double longitude) {
    PointUtils.validateLongitude(longitude);
    this.longitude = longitude;
  }

  @JsonProperty("latitude")
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