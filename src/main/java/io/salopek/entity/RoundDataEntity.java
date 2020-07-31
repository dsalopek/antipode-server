package io.salopek.entity;

public class RoundDataEntity {
  private long roundId;
  private long gameId;
  private double distance;

  public RoundDataEntity(long roundId, long gameId, double distance) {
    this.roundId = roundId;
    this.gameId = gameId;
    this.distance = distance;
  }

  public RoundDataEntity(long gameId, double distance) {
    this.gameId = gameId;
    this.distance = distance;
  }

  public long getRoundId() {
    return roundId;
  }

  public void setRoundId(long roundId) {
    this.roundId = roundId;
  }

  public long getGameId() {
    return gameId;
  }

  public void setGameId(long gameId) {
    this.gameId = gameId;
  }

  public double getDistance() {
    return distance;
  }

  public void setDistance(double distance) {
    this.distance = distance;
  }

  @Override
  public String toString() {
    return "RoundData{" +
      "roundId=" + roundId +
      ", gameId=" + gameId +
      ", distance=" + distance +
      '}';
  }
}
