package io.salopek.security;

import io.dropwizard.auth.Authorizer;
import io.salopek.model.UserData;

public class CoreAuthorizer implements Authorizer<UserData> {
  @Override
  public boolean authorize(UserData principal, String role) {
    return true;
  }
}
