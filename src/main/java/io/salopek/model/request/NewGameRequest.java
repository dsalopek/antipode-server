package io.salopek.model.request;

import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.NotNull;

public class NewGameRequest {
  @NotNull
  @Length(min = 3, max = 20, message = "Name length must be at least 3 characters and no more than 20")
  private String playerName;

  public NewGameRequest() {
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