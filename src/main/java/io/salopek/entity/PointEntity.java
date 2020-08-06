package io.salopek.entity;

import io.salopek.constant.PointType;

public class PointEntity {
  private long pointId;
  private long roundId;
  private PointType type;
  private double latitude;
  private double longitude;

  public PointEntity() {
  }

  public PointEntity(double latitude, double longitude) {
    this(0L, 0L, null, latitude, longitude);
  }

  public PointEntity(long roundId, PointType type, double latitude, double longitude) {
    this(0L, roundId, type, latitude, longitude);
  }

  public PointEntity(long pointId, long roundId, PointType type, double latitude, double longitude) {
    this.pointId = pointId;
    this.roundId = roundId;
    this.type = type;
    this.latitude = latitude;
    this.longitude = longitude;
  }

  public long getPointId() {
    return pointId;
  }

  public void setPointId(long pointId) {
    this.pointId = pointId;
  }

  public long getRoundId() {
    return roundId;
  }

  public void setRoundId(long roundId) {
    this.roundId = roundId;
  }

  public PointType getType() {
    return type;
  }

  public void setType(PointType type) {
    this.type = type;
  }

  public double getLatitude() {
    return latitude;
  }

  public void setLatitude(double latitude) {
    this.latitude = latitude;
  }

  public double getLongitude() {
    return longitude;
  }

  public void setLongitude(double longitude) {
    this.longitude = longitude;
  }

  @Override
  public String toString() {
    return "Point{" +
      "pointId=" + pointId +
      ", roundId=" + roundId +
      ", type='" + type + '\'' +
      ", latitude=" + latitude +
      ", longitude=" + longitude +
      '}';
  }
}
