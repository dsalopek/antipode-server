package io.salopek.db;

import io.salopek.dao.GameDataDAO;
import io.salopek.dao.GameIdDAO;
import io.salopek.dao.PointDataDAO;
import io.salopek.dao.RoundDataDAO;
import io.salopek.entity.GameDataEntity;
import io.salopek.entity.PointEntity;
import io.salopek.entity.RoundDataEntity;
import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyList;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

class DatabaseServiceImplTest {

  private GameDataDAO gameDataDAO = mock(GameDataDAO.class);
  private RoundDataDAO roundDataDAO = mock(RoundDataDAO.class);
  private PointDataDAO pointDataDAO = mock(PointDataDAO.class);
  private GameIdDAO gameIdDAO = mock(GameIdDAO.class);

  private DatabaseService databaseService = new DatabaseServiceImpl(gameDataDAO, roundDataDAO, pointDataDAO, gameIdDAO);

  @Test
  void saveNewGame() {
    GameDataEntity gameDataEntity = new GameDataEntity();
    when(gameDataDAO.insertGameData(any())).thenReturn(1L);
    assertDoesNotThrow(() -> databaseService.saveNewGame(gameDataEntity));
  }

  @Test
  void saveNewRound() {
    RoundDataEntity roundDataEntity = new RoundDataEntity(1L, 500);
    when(roundDataDAO.insertRoundData(any())).thenReturn(2L);
    assertDoesNotThrow(() -> databaseService.saveNewRound(roundDataEntity));
  }

  @Test
  void saveNewPoint() {
    PointEntity pointEntity = new PointEntity();
    when(pointDataDAO.insertPointData(any())).thenReturn(1L);
    assertDoesNotThrow(() -> databaseService.saveNewPoint(pointEntity));
  }

  @Test
  void saveNewGameId() {
    long gameId = 1;
    String gameUUID = "asdfg1234";
    assertDoesNotThrow(() -> databaseService.saveNewGameId(gameId, gameUUID));
  }

  @Test
  void getGameId() {
    String gameUUID = "asdfg1234";
    when(gameIdDAO.getGameId(any())).thenReturn(1L);
    assertDoesNotThrow(() -> databaseService.getGameId(gameUUID));
  }

  @Test
  void saveGameData() {
    GameDataEntity gameDataEntity = new GameDataEntity();
    assertDoesNotThrow(() -> databaseService.saveGameData(gameDataEntity));
  }

  @Test
  void getGameDataByGameId() {
    long gameId = 1L;
    GameDataEntity gameDataEntity = new GameDataEntity();
    when(gameDataDAO.getGameDataByGameId(anyLong())).thenReturn(gameDataEntity);
    assertDoesNotThrow(() -> databaseService.getGameDataByGameId(gameId));
  }

  @Test
  void getRoundDataByGameId() {
    long gameId = 1L;
    List<RoundDataEntity> rounds = Arrays.asList(new RoundDataEntity(gameId, 1000d));
    when(roundDataDAO.getRoundDataByGameId(anyLong())).thenReturn(rounds);
    assertDoesNotThrow(() -> databaseService.getRoundDataByGameId(gameId));
  }

  @Test
  void getPointDataByRoundIds() {
    List<Long> roundIds = Arrays.asList(1L, 2L, 3L);
    List<PointEntity> points = Arrays.asList(new PointEntity(), new PointEntity(), new PointEntity());
    when(pointDataDAO.getPointsByRoundIds(anyList())).thenReturn(points);
    assertDoesNotThrow(() -> databaseService.getPointDataByRoundIds(roundIds));
  }
}