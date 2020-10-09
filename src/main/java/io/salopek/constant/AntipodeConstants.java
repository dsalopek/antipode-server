package io.salopek.constant;

public class AntipodeConstants {

  private AntipodeConstants() {
  }

  //  Exception Messages
  public static final String EXC_ORIGIN_REQUIRED = "Origin point is required.";
  public static final String EXC_LONGITUDE_INVALID = "Longitude must be no more than 180 and no less than -180.";
  public static final String EXC_LATITUDE_INVALID = "Latitude must be no more than 90 and no less than -90.";
  public static final String EXC_GET_WRITER = "getWriter() has already been called on this response.";
  public static final String EXC_GET_OUTPUTSTREAM = "getOutputStream() has already been called on this response.";
  public static final String EXC_USER_EXISTS = "User already exists.";
  public static final String EXC_USER_NOT_FOUND = "Username not found.";
  public static final String EXC_INVALID_PASSWORD = "Invalid password.";
  public static final String EXC_INVALID_TOKEN = "Invalid token.";
  public static final String EXC_USERNAME_UNAVAIL = "Username is already in use.";

  //  Values
  public static final double LONGITUDE = 180d;
  public static final double LATITUDE = 90d;

  //  API Endpoints
  public static final String GAME_ENDPOINT = "/game";
  public static final String NEW_GAME = "/newGame";
  public static final String SUBMIT_ROUND = "/submitRound";
  public static final String FINISH_GAME = "/finishGame";
  public static final String HIGH_SCORES = "/highScores";
  public static final String AUTH_ENDPOINT = "/auth";
  public static final String REGISTER = "/register";
  public static final String LOGIN = "/login";
  public static final String VALIDATE_TOKEN = "/validate";
  public static final String AVAILABILITY = "/availability";

  //  Exclusion Keys
  public static final String PASSWORD = "password";
  public static final String ACCESS_TOKEN = "accessToken";
  public static final String MASK = "***********";

  // Log Keys
  public static final String RESPONSE = "response";
  public static final String STATUS = "status";
  public static final String HTTP_METHOD = "HTTPMethod";
  public static final String REQUEST_ID = "requestId=";
  public static final String REQUEST = "request";
  public static final String THREAD_PREFIX = "ap-";
}
