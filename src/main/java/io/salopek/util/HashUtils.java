package io.salopek.util;

import at.favre.lib.crypto.bcrypt.BCrypt;

import java.time.Instant;

public class HashUtils {

  public static String hashString(String s) {
    return BCrypt.withDefaults().hashToString(12, s.toCharArray());
  }

  public static String randomHashByString(String s) {
    String currentTime = Instant.now().toString() + s;
    return BCrypt.withDefaults().hashToString(12, currentTime.toCharArray());
  }

}
