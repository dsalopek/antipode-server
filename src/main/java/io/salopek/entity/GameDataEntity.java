package io.salopek.entity;

import java.sql.Timestamp;
import java.time.Instant;

public class GameDataEntity {
  private long gameId;
  private String playerName;
  private Timestamp startTime;
  private Timestamp endTime;

  public GameDataEntity() {
  }

  public GameDataEntity(String playerName) {
    this.playerName = playerName;
    this.startTime = Timestamp.from(Instant.now());
  }

  public GameDataEntity(String playerName, Timestamp startTime, Timestamp endTime) {
    this.playerName = playerName;
    this.startTime = startTime;
    this.endTime = endTime;
  }

  public GameDataEntity(long gameId, String playerName, Timestamp startTime, Timestamp endTime) {
    this.gameId = gameId;
    this.playerName = playerName;
    this.startTime = startTime;
    this.endTime = endTime;
  }

  public long getGameId() {
    return gameId;
  }

  public void setGameId(long gameId) {
    this.gameId = gameId;
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
      "gameId=" + gameId +
      ", playerName='" + playerName + '\'' +
      ", startTime=" + startTime +
      ", endTime=" + endTime +
      '}';
  }
}
