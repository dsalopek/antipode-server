package io.salopek.di;

import io.dropwizard.auth.AuthDynamicFeature;
import io.dropwizard.auth.oauth.OAuthCredentialAuthFilter;
import io.salopek.db.DatabaseService;
import io.salopek.model.UserData;
import io.salopek.security.CoreAuthenticator;
import io.salopek.security.CoreAuthorizer;
import io.salopek.security.UnauthorizedHandler;

import javax.inject.Inject;

public class AuthFeatureFactory {

  @Inject
  private DatabaseService databaseService;

  public AuthDynamicFeature get() {
    if (null != databaseService) {
      return new AuthDynamicFeature(new OAuthCredentialAuthFilter.Builder<UserData>()
        .setAuthenticator(new CoreAuthenticator(databaseService))
        .setUnauthorizedHandler(new UnauthorizedHandler())
        .setAuthorizer(new CoreAuthorizer())
        .setPrefix("Bearer")
        .buildAuthFilter());
    } else {
      throw new NullPointerException("Database service is empty!");
    }
  }
}
