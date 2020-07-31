package io.salopek.model.response;

import java.util.List;

public class GameResultsResponse {
  private String playerName;
  private double totalDistance;
  private List<CompletedRoundData> completedRoundData;

  public GameResultsResponse(String playerName, List<CompletedRoundData> completedRoundData, double totalDistance) {
    this.playerName = playerName;
    this.completedRoundData = completedRoundData;
    this.totalDistance = totalDistance;
  }

  public String getPlayerName() {
    return playerName;
  }

  public void setPlayerName(String playerName) {
    this.playerName = playerName;
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
