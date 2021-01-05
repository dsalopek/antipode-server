package io.salopek.model.response;

import java.util.List;

public class GameResultsResponse {
  private double totalDistance;
  private List<CompletedRoundData> completedRoundData;

  public GameResultsResponse() {
  }

  public GameResultsResponse(List<CompletedRoundData> completedRoundData, double totalDistance) {
    this.completedRoundData = completedRoundData;
    this.totalDistance = totalDistance;
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
