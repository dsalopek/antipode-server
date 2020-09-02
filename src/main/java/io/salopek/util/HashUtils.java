package io.salopek.util;

import at.favre.lib.crypto.bcrypt.BCrypt;
import io.salopek.logging.Loggable;

import java.time.Instant;

public class HashUtils {

  @Loggable
  public static String hashString(String s) {
    return BCrypt.withDefaults().hashToString(12, s.toCharArray());
  }

  @Loggable
  public static String randomHashByString(String s) {
    String currentTime = Instant.now().toString() + s;
    return BCrypt.withDefaults().hashToString(12, currentTime.toCharArray());
  }

}
