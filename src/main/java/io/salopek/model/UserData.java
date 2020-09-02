package io.salopek.model;

import java.security.Principal;

public class UserData implements Principal {
  private long userId;
  private String userName;

  public UserData() {
  }

  public UserData(long userId, String userName) {
    this.userId = userId;
    this.userName = userName;
  }

  public long getUserId() {
    return userId;
  }

  public void setUserId(long userId) {
    this.userId = userId;
  }

  public String getUserName() {
    return userName;
  }

  public void setUserName(String userName) {
    this.userName = userName;
  }

  @Override
  public String toString() {
    return "UserData{" +
      "userId=" + userId +
      ", userName='" + userName + '\'' +
      '}';
  }

  @Override
  public String getName() {
    return getUserName();
  }
}
