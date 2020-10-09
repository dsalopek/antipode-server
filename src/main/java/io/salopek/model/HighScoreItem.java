package io.salopek.model;

public class HighScoreItem {
  private long rank;
  private String userName;
  private double distance;

  public HighScoreItem() {
  }

  public HighScoreItem(long rank, String userName, double distance) {
    this.rank = rank;
    this.userName = userName;
    this.distance = distance;
  }

  public long getRank() {
    return rank;
  }

  public void setRank(long rank) {
    this.rank = rank;
  }

  public String getUserName() {
    return userName;
  }

  public void setUserName(String userName) {
    this.userName = userName;
  }

  public double getDistance() {
    return distance;
  }

  public void setDistance(double distance) {
    this.distance = distance;
  }

  @Override
  public String toString() {
    return "HighScoreItem{" +
      "rank=" + rank +
      ", userName='" + userName + '\'' +
      ", distance=" + distance +
      '}';
  }
}
