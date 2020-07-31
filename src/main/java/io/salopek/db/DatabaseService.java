package io.salopek.db;

import io.salopek.entity.GameDataEntity;
import io.salopek.entity.PointEntity;
import io.salopek.entity.RoundDataEntity;

import java.util.List;

public interface DatabaseService {
  long saveNewGame(GameDataEntity gameDataEntity);

  long saveNewRound(RoundDataEntity roundDataEntity);

  long saveNewPoint(PointEntity pointEntity);

  void saveNewGameId(long gameId, String gameUUID);

  long getGameId(String gameUUID);

  void saveGameData(GameDataEntity gameDataEntity);

  GameDataEntity getGameDataByGameId(long gameId);

  List<RoundDataEntity> getRoundDataByGameId(long gameId);

  List<PointEntity> getPointDataByRoundIds(List<Long> roundIds);
}