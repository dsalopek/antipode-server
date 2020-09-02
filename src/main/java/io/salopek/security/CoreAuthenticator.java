package io.salopek.security;

import io.dropwizard.auth.AuthenticationException;
import io.dropwizard.auth.Authenticator;
import io.salopek.db.DatabaseService;
import io.salopek.entity.UserDataEntity;
import io.salopek.mapper.ModelMapper;
import io.salopek.model.UserData;
import org.mapstruct.factory.Mappers;

import javax.inject.Inject;
import java.util.Optional;

public class CoreAuthenticator implements Authenticator<String, UserData> {
  private static final ModelMapper MAPPER = Mappers.getMapper(ModelMapper.class);
  private final DatabaseService databaseService;

  @Inject
  public CoreAuthenticator(DatabaseService databaseService) {
    this.databaseService = databaseService;
  }

  @Override
  public Optional<UserData> authenticate(String accessToken) throws AuthenticationException {

    UserDataEntity userDataEntity = databaseService.getUserByAccessToken(accessToken);

    if (userDataEntity == null) {
      return Optional.empty();
    }

    return Optional.of(MAPPER.toUserData(userDataEntity));
  }
}
