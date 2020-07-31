package io.salopek.model.request;

public class FinishGameRequest {
  private String gameUUID;

  private FinishGameRequest() {
  }

  public FinishGameRequest(String gameUUID) {
    this.gameUUID = gameUUID;
  }

  public String getGameUUID() {
    return gameUUID;
  }
}
