package io.salopek.model.request;

public class NewClientRequest {

  private String clientToken;
  private String playerName;

  private NewClientRequest() {
  }

  public String getClientToken() {
    return clientToken;
  }

  public void setClientToken(String clientToken) {
    this.clientToken = clientToken;
  }

  public String getPlayerName() {
    return playerName;
  }

  public void setPlayerName(String playerName) {
    this.playerName = playerName;
  }

  @Override
  public String toString() {
    return "NewClientRequest{" +
      "clientToken='" + clientToken + '\'' +
      ", playerName='" + playerName + '\'' +
      '}';
  }
}
