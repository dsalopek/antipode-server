package io.salopek.processor;

import at.favre.lib.crypto.bcrypt.BCrypt;
import io.salopek.db.DatabaseService;
import io.salopek.entity.UserDataEntity;
import io.salopek.logging.Loggable;
import io.salopek.model.request.LoginRequest;
import io.salopek.model.request.RegisterRequest;
import io.salopek.model.request.UsernameAvailabilityRequest;
import io.salopek.model.request.ValidateTokenRequest;
import io.salopek.model.response.AccessTokenResponse;
import io.salopek.model.response.UsernameAvailabilityResponse;
import io.salopek.model.response.ValidateTokenResponse;
import io.salopek.util.HashUtils;

import javax.inject.Inject;
import javax.ws.rs.BadRequestException;
import javax.ws.rs.NotFoundException;

import static io.salopek.constant.AntipodeConstants.EXC_INVALID_PASSWORD;
import static io.salopek.constant.AntipodeConstants.EXC_INVALID_TOKEN;
import static io.salopek.constant.AntipodeConstants.EXC_USERNAME_UNAVAIL;
import static io.salopek.constant.AntipodeConstants.EXC_USER_EXISTS;
import static io.salopek.constant.AntipodeConstants.EXC_USER_NOT_FOUND;

public class AuthenticationProcessorImpl implements AuthenticationProcessor {

  private final DatabaseService databaseService;

  @Inject
  public AuthenticationProcessorImpl(DatabaseService databaseService) {
    this.databaseService = databaseService;
  }

  @Loggable
  @Override
  public AccessTokenResponse register(RegisterRequest registerRequest) {

    UserDataEntity userData = databaseService.getUserByUsername(registerRequest.getUserName());
    if (userData != null) {
      throw new BadRequestException(EXC_USER_EXISTS);
    }
    userData = new UserDataEntity(registerRequest.getUserName());
    String hashedPassword = HashUtils.hashString(registerRequest.getPassword());
    userData.setPassword(hashedPassword);
    String accessToken = HashUtils.randomHashByString(userData.getUserName());
    userData.setAccessToken(accessToken);

    databaseService.createNewUser(userData);

    return new AccessTokenResponse(accessToken);
  }

  @Loggable
  @Override
  public AccessTokenResponse login(LoginRequest loginRequest) {

    UserDataEntity userData = databaseService.getUserByUsername(loginRequest.getUserName());
    if (userData == null) {
      throw new NotFoundException(EXC_USER_NOT_FOUND);
    }
    BCrypt.Result result = BCrypt.verifyer().verify(loginRequest.getPassword().toCharArray(), userData.getPassword());
    if (!result.verified) {
      throw new BadRequestException(EXC_INVALID_PASSWORD);
    }
    String accessToken = HashUtils.randomHashByString(userData.getUserName());
    databaseService.updateAccessTokenByUserId(accessToken, userData.getUserId());

    return new AccessTokenResponse(accessToken);
  }

  @Loggable
  @Override
  public ValidateTokenResponse validateTokenRequest(ValidateTokenRequest validateTokenRequest) {

    String accessToken = validateTokenRequest.getAccessToken();
    boolean isValidToken = databaseService.doesAccessTokenExist(accessToken);

    return new ValidateTokenResponse(isValidToken);
  }

  @Loggable
  @Override
  public UsernameAvailabilityResponse availability(UsernameAvailabilityRequest usernameAvailabilityRequest) {

    String username = usernameAvailabilityRequest.getUserName();
    boolean isUsernameAvailable = databaseService.isUsernameAvailable(username);

    return new UsernameAvailabilityResponse(isUsernameAvailable);
  }
}
