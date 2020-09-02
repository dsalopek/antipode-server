package io.salopek.model;

import java.sql.Timestamp;
import java.time.Instant;

public class GameData {
  private UserData userData;
  private Timestamp startTime;
  private Timestamp endTime;

  public GameData(UserData userData) {
    this(userData, Timestamp.from(Instant.now()), null);
  }

  public GameData(UserData userData, Timestamp startTime, Timestamp endTime) {
    this.userData = userData;
    this.startTime = startTime;
    this.endTime = endTime;
  }

  public UserData getUserData() {
    return userData;
  }

  public void setUserData(UserData userData) {
    this.userData = userData;
  }

  public Timestamp getStartTime() {
    return startTime;
  }

  public void setStartTime(Timestamp startTime) {
    this.startTime = startTime;
  }

  public Timestamp getEndTime() {
    return endTime;
  }

  public void setEndTime(Timestamp endTime) {
    this.endTime = endTime;
  }

  @Override
  public String toString() {
    return "GameData{" +
      ", userData='" + userData + '\'' +
      ", startTime=" + startTime +
      ", endTime=" + endTime +
      '}';
  }
}
