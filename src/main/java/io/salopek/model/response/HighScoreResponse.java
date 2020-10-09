package io.salopek.model.response;

import io.salopek.model.HighScoreItem;

import java.util.List;

public class HighScoreResponse {
  private List<HighScoreItem> highScores;
  private HighScoreItem personalBest;

  public HighScoreResponse() {
  }

  public HighScoreResponse(List<HighScoreItem> highScores, HighScoreItem personalBest) {
    this.highScores = highScores;
    this.personalBest = personalBest;
  }

  public List<HighScoreItem> getHighScores() {
    return highScores;
  }

  public void setHighScores(List<HighScoreItem> highScores) {
    this.highScores = highScores;
  }

  public HighScoreItem getPersonalBest() {
    return personalBest;
  }

  public void setPersonalBest(HighScoreItem personalBest) {
    this.personalBest = personalBest;
  }

  @Override
  public String toString() {
    return "HighScoreResponse{" +
      "highScores=" + highScores +
      ", personalBest=" + personalBest +
      '}';
  }
}
