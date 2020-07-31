package io.salopek.model.response;

import io.salopek.model.Point;

public class RoundResponse {
  private String gameUUID;
  private Point origin;

  private RoundResponse() {

  }

  public RoundResponse(String gameId, Point origin) {
    this.gameUUID = gameId;
    this.origin = origin;
  }

  public String getGameUUID() {
    return gameUUID;
  }

  public void setGameUUID(String gameUUID) {
    this.gameUUID = gameUUID;
  }

  public Point getOrigin() {
    return origin;
  }

  public void setOrigin(Point origin) {
    this.origin = origin;
  }

  @Override
  public String toString() {
    return "RoundResponse{" +
      "gameUUID=" + gameUUID +
      ", origin=" + origin +
      '}';
  }
}
