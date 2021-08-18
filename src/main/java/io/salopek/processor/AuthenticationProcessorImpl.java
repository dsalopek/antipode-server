package io.salopek.processor;

import at.favre.lib.crypto.bcrypt.BCrypt;
import io.salopek.constant.LogKeys;
import io.salopek.db.DatabaseService;
import io.salopek.entity.UserDataEntity;
import io.salopek.logging.LogBuilder;
import io.salopek.logging.LogUtils;
import io.salopek.model.request.LoginRequest;
import io.salopek.model.request.RegisterRequest;
import io.salopek.model.request.UsernameAvailabilityRequest;
import io.salopek.model.request.ValidateTokenRequest;
import io.salopek.model.response.AccessTokenResponse;
import io.salopek.model.response.UsernameAvailabilityResponse;
import io.salopek.model.response.ValidateTokenResponse;
import io.salopek.util.HashUtils;
import org.apache.commons.lang3.time.StopWatch;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.inject.Inject;
import javax.ws.rs.BadRequestException;

import java.util.Optional;

import static io.salopek.constant.AntipodeConstants.EXC_USER_EXISTS;
import static io.salopek.constant.AntipodeConstants.EXC_INVALID_CREDENTIALS;

public class AuthenticationProcessorImpl implements AuthenticationProcessor {
  private static final Logger LOGGER = LoggerFactory.getLogger(AuthenticationProcessorImpl.class);
  private final DatabaseService databaseService;

  @Inject
  public AuthenticationProcessorImpl(DatabaseService databaseService) {
    this.databaseService = databaseService;
  }

  @Override
  public AccessTokenResponse register(RegisterRequest registerRequest) {
    StopWatch sw = LogUtils.stopWatch();
    UserDataEntity userData = databaseService.getUserByUsername(registerRequest.getUserName());
    if (userData != null) {
      LogBuilder lb = LogBuilder.get().log("User already exists by that username")
        .kv(LogKeys.USER_ID, userData.getUserId());
      LOGGER.error(lb.build());
      throw new BadRequestException(EXC_USER_EXISTS);
    }
    userData = new UserDataEntity(registerRequest.getUserName());
    String hashedPassword = HashUtils.hashString(registerRequest.getPassword());
    userData.setPassword(hashedPassword);
    String accessToken = HashUtils.randomHashByString(userData.getUserName());
    userData.setAccessToken(accessToken);

    databaseService.createNewUser(userData);

    LogBuilder lb = LogBuilder.get().log("Successfully registered new user").kv(LogKeys.USER_ID, userData.getUserId());
    lb.log(LogUtils.methodExit("register", sw));
    LOGGER.info(lb.build());
    return new AccessTokenResponse(accessToken);
  }

  @Override
  public AccessTokenResponse login(LoginRequest loginRequest) {
    StopWatch sw = LogUtils.stopWatch();

    UserDataEntity userData = databaseService.getUserByUsername(loginRequest.getUserName());
    BCrypt.Result result = verifyPassword(loginRequest, userData);
    if (!result.verified) {
      LogBuilder lb = LogBuilder.get().log("Invalid credentials")
        .kv(LogKeys.USER_ID, Optional.ofNullable(userData.getUserId()).orElse(-1L));
      LOGGER.error(lb.build());
      throw new BadRequestException(EXC_INVALID_CREDENTIALS);
    }
    String accessToken = HashUtils.randomHashByString(userData.getUserName());
    databaseService.updateAccessTokenByUserId(accessToken, userData.getUserId());

    LogBuilder lb = LogBuilder.get().log("Login successful").kv(LogKeys.USER_ID, userData.getUserId());
    lb.log(LogUtils.methodExit("login", sw));
    LOGGER.info(lb.build());
    return new AccessTokenResponse(accessToken);
  }

  @Override
  public ValidateTokenResponse validateTokenRequest(ValidateTokenRequest validateTokenRequest) {
    StopWatch sw = LogUtils.stopWatch();

    String accessToken = validateTokenRequest.getAccessToken();
    boolean isValidToken = databaseService.doesAccessTokenExist(accessToken);

    LogBuilder lb = LogBuilder.get().kv(LogKeys.IS_VALIDATED, isValidToken).kv(LogKeys.ACCESS_TOKEN, accessToken);
    lb.log(LogUtils.methodExit("validateTokenRequest", sw));
    LOGGER.info(lb.build());
    return new ValidateTokenResponse(isValidToken);
  }

  @Override
  public UsernameAvailabilityResponse availability(UsernameAvailabilityRequest usernameAvailabilityRequest) {
    StopWatch sw = LogUtils.stopWatch();

    String username = usernameAvailabilityRequest.getUserName();
    boolean isUsernameAvailable = databaseService.isUsernameAvailable(username);

    LogBuilder lb = LogBuilder.get().kv(LogKeys.IS_USERNAME_AVAILABLE, isUsernameAvailable);
    lb.log(LogUtils.methodExit("availability", sw));
    LOGGER.info(lb.build());
    return new UsernameAvailabilityResponse(isUsernameAvailable);
  }

  private BCrypt.Result verifyPassword(LoginRequest loginRequest, UserDataEntity userData) {
    String storedPassword = "";
    if (null != userData) {
      storedPassword = userData.getPassword();
    }
    return BCrypt.verifyer().verify(loginRequest.getPassword().toCharArray(), storedPassword);
  }
}
