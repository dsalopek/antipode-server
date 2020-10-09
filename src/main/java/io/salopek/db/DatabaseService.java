package io.salopek.db;

import io.salopek.entity.GameDataEntity;
import io.salopek.entity.PointEntity;
import io.salopek.entity.RoundDataEntity;
import io.salopek.entity.UserDataEntity;
import io.salopek.model.HighScoreItem;

import java.util.List;

public interface DatabaseService {
  boolean isConnected();

  long saveNewGame(GameDataEntity gameDataEntity);

  long saveNewRound(RoundDataEntity roundDataEntity);

  long saveNewPoint(PointEntity pointEntity);

  void saveNewGameId(long gameId, String gameUUID);

  long getGameId(String gameUUID);

  void saveGameData(GameDataEntity gameDataEntity);

  GameDataEntity getGameDataByGameId(long gameId);

  List<RoundDataEntity> getRoundDataByGameId(long gameId);

  List<PointEntity> getPointDataByRoundIds(List<Long> roundIds);

  boolean createNewUser(UserDataEntity userData);

  boolean updateAccessTokenByUserId(String accessToken, long userId);

  UserDataEntity getUserByAccessToken(String accessToken);

  UserDataEntity getUserByUsername(String userName);

  UserDataEntity getUserByUserId(long userId);

  boolean doesAccessTokenExist(String accessToken);

  boolean isUsernameAvailable(String username);

  HighScoreItem getPersonalBestByUserName(String userName);

  List<HighScoreItem> getTopTen();
}