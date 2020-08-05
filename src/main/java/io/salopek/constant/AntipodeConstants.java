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

  //  Values
  public static final double LONGITUDE = 180d;
  public static final double LATITUDE = 90d;

  //  API Endpoints
  public static final String GAME_ENDPOINT = "/game";
  public static final String NEW_GAME = "/newGame";
  public static final String SUBMIT_ROUND = "/submitRound";
  public static final String FINISH_GAME = "/finishGame";
}
