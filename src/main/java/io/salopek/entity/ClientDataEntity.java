package io.salopek.entity;

public class ClientDataEntity {
  private long clientId;
  private String apiKey;
  private String playerName;

  public ClientDataEntity() {
  }

  public ClientDataEntity(long clientId, String apiKey, String playerName) {
    this.clientId = clientId;
    this.apiKey = apiKey;
    this.playerName = playerName;
  }

  public long getClientId() {
    return clientId;
  }

  public void setClientId(long clientId) {
    this.clientId = clientId;
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
    return "ClientDataEntity{" +
      "clientId=" + clientId +
      ", apiKey='" + apiKey + '\'' +
      ", playerName='" + playerName + '\'' +
      '}';
  }
}
