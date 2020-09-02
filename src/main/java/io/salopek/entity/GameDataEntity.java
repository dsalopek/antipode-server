package io.salopek.entity;

import java.sql.Timestamp;
import java.time.Instant;

public class GameDataEntity {
  private long gameId;
  private long userId;
  private Timestamp startTime;
  private Timestamp endTime;

  public GameDataEntity() {
  }

  public GameDataEntity(long userId) {
    this(0L, userId, Timestamp.from(Instant.now()), null);
  }

  public GameDataEntity(long userId, Timestamp startTime, Timestamp endTime) {
    this(0L, userId, startTime, endTime);
  }

  public GameDataEntity(long gameId, long userId, Timestamp startTime, Timestamp endTime) {
    this.gameId = gameId;
    this.userId = userId;
    this.startTime = startTime;
    this.endTime = endTime;
  }

  public long getGameId() {
    return gameId;
  }

  public void setGameId(long gameId) {
    this.gameId = gameId;
  }

  public long getUserId() {
    return userId;
  }

  public void setUserId(long userId) {
    this.userId = userId;
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
      ", userId='" + userId + '\'' +
      ", startTime=" + startTime +
      ", endTime=" + endTime +
      '}';
  }
}
