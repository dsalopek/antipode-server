package io.salopek.model.response;

import java.util.List;

public class GameResultsResponse {
  private String playName;
  private double totalDistance;
  private List<CompletedRoundData> completedRoundData;

  public GameResultsResponse(String playName, List<CompletedRoundData> completedRoundData, double totalDistance) {
    this.playName = playName;
    this.completedRoundData = completedRoundData;
    this.totalDistance = totalDistance;
  }

  public String getPlayName() {
    return playName;
  }

  public void setPlayName(String playName) {
    this.playName = playName;
  }

  public List<CompletedRoundData> getCompletedRoundData() {
    return completedRoundData;
  }

  public void setCompletedRoundData(List<CompletedRoundData> completedRoundData) {
    this.completedRoundData = completedRoundData;
  }

  public double getTotalDistance() {
    return totalDistance;
  }

  public void setTotalDistance(double totalDistance) {
    this.totalDistance = totalDistance;
  }
}
