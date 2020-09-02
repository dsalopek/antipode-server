package io.salopek.processor;

import at.favre.lib.crypto.bcrypt.BCrypt;
import io.salopek.db.DatabaseService;
import io.salopek.entity.UserDataEntity;
import io.salopek.model.request.LoginRequest;
import io.salopek.model.request.RegisterRequest;
import io.salopek.model.response.AccessTokenResponse;
import io.salopek.util.HashUtils;

import javax.inject.Inject;
import javax.ws.rs.BadRequestException;
import javax.ws.rs.NotFoundException;

public class AuthenticationProcessorImpl implements AuthenticationProcessor {

  private final DatabaseService databaseService;

  @Inject
  public AuthenticationProcessorImpl(DatabaseService databaseService) {
    this.databaseService = databaseService;
  }

  @Override
  public AccessTokenResponse register(RegisterRequest registerRequest) {

    UserDataEntity userData = databaseService.isUsernameAvailable(registerRequest.getUserName());

    if (userData != null) {
      throw new BadRequestException("User already exists");
    }
    userData = new UserDataEntity(registerRequest.getUserName());

    String hashedPassword = HashUtils.hashString(registerRequest.getPassword());
    userData.setPassword(hashedPassword);

    String accessToken = HashUtils.randomHashByString(userData.getUserName());
    userData.setAccessToken(accessToken);

    databaseService.createNewUser(userData);
    return new AccessTokenResponse(accessToken);
  }

  @Override
  public AccessTokenResponse login(LoginRequest loginRequest) {

    UserDataEntity userData = databaseService.getUserByUsername(loginRequest.getUserName());

    if (userData == null) {
      throw new NotFoundException("User not found");
    }

    BCrypt.Result result = BCrypt.verifyer().verify(loginRequest.getPassword().toCharArray(), userData.getPassword());

    if (!result.verified) {
      throw new BadRequestException("Invalid password");
    }

    String accessToken = HashUtils.randomHashByString(userData.getUserName());
    databaseService.updateAccessTokenByUserId(accessToken, userData.getUserId());

    return new AccessTokenResponse(accessToken);
  }
}
