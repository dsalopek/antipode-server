package io.salopek.model.request;

public class NewGameRequest {
  private String playerName;

  private NewGameRequest() {
  }

  public NewGameRequest(String playerName) {
    this.playerName = playerName;
  }

  public String getPlayerName() {
    return playerName;
  }

  public void setPlayerName(String playerName) {
    this.playerName = playerName;
  }

  @Override
  public String toString() {
    return "NewGameRequest{" +
      "playerName='" + playerName + '\'' +
      '}';
  }
}