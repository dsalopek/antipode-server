package io.salopek.model.request;

public class UpdateClientRequest {

  private String apiKey;
  private String playerName;

  private UpdateClientRequest() {
  }

  public String getApiKey() {
    return apiKey;
  }

  public void setApiKey(String apiKey) {
    this.apiKey = apiKey;
  }

  public String getPlayerName() {
    return playerName;
  }

  public void setPlayerName(String playerName) {
    this.playerName = playerName;
  }

  @Override
  public String toString() {
    return "UpdateClientRequest{" +
      "apiKey='" + apiKey + '\'' +
      ", playerName='" + playerName + '\'' +
      '}';
  }
}
