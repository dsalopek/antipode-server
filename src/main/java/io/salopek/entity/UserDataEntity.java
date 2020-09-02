package io.salopek.entity;

public class UserDataEntity {

  private long userId;
  private String userName;
  private String password;
  private String accessToken;

  public UserDataEntity(String userName) {
    this(0L, userName, null, null);
  }

  public UserDataEntity(long userId, String userName, String password, String accessToken) {
    this.userId = userId;
    this.userName = userName;
    this.password = password;
    this.accessToken = accessToken;
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

  public String getPassword() {
    return password;
  }

  public void setPassword(String password) {
    this.password = password;
  }

  public String getAccessToken() {
    return accessToken;
  }

  public void setAccessToken(String accessToken) {
    this.accessToken = accessToken;
  }

  @Override
  public String toString() {
    return "UserDataEntity{" +
      "userId=" + userId +
      ", userName='" + userName + '\'' +
      ", accessToken='" + accessToken + '\'' +
      '}';
  }
}
