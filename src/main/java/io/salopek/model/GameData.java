package io.salopek.model;

import java.sql.Timestamp;
import java.time.Instant;

public class GameData {
  private String playerName;
  private Timestamp startTime;
  private Timestamp endTime;

  public GameData(String playerName) {
    this.playerName = playerName;
    this.startTime = Timestamp.from(Instant.now());
  }

  public GameData(String playerName, Timestamp startTime, Timestamp endTime) {
    this.playerName = playerName;
    this.startTime = startTime;
    this.endTime = endTime;
  }

  public String getPlayerName() {
    return playerName;
  }

  public void setPlayerName(String playerName) {
    this.playerName = playerName;
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
      ", playerName='" + playerName + '\'' +
      ", startTime=" + startTime +
      ", endTime=" + endTime +
      '}';
  }
}
