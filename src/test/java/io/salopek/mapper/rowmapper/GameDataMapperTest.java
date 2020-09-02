package io.salopek.mapper.rowmapper;

import io.salopek.entity.GameDataEntity;
import io.salopek.util.MockResultSet;
import org.jdbi.v3.core.statement.StatementContext;
import org.junit.jupiter.api.Test;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.time.Instant;
import java.util.HashMap;
import java.util.Map;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.mock;

class GameDataMapperTest {

  GameDataMapper gameDataMapper = new GameDataMapper();
  Map<String, Object> data = new HashMap<>();

  @Test
  void map() throws SQLException {
    Timestamp timestamp = Timestamp.from(Instant.now());
    data.put("game_id", 15L);
    data.put("user_id", 1L);
    data.put("start_ts", timestamp);
    data.put("end_ts", timestamp);

    ResultSet resultSet = new MockResultSet(data);
    StatementContext ctx = mock(StatementContext.class);

    GameDataEntity expectedOutput = new GameDataEntity(15L, 1L, timestamp, timestamp);

    assertThat(gameDataMapper.map(resultSet, ctx)).usingRecursiveComparison().isEqualTo(expectedOutput);

  }

}