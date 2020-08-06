package io.salopek.mapper.rowmapper;

import io.salopek.util.MockResultSet;
import org.jdbi.v3.core.statement.StatementContext;
import org.junit.jupiter.api.Test;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.mock;

class GameIdMapperTest {
  GameIdMapper gameIdMapper = new GameIdMapper();
  Map<String, Object> data = new HashMap<>();

  @Test
  void map() throws SQLException {
    data.put("game_id", 15L);

    ResultSet resultSet = new MockResultSet(data);
    StatementContext ctx = mock(StatementContext.class);

    assertThat(gameIdMapper.map(resultSet, ctx)).isEqualTo(15L);

  }
}