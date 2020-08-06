package io.salopek.mapper.rowmapper;

import io.salopek.entity.PointEntity;
import io.salopek.entity.RoundDataEntity;
import io.salopek.util.MockResultSet;
import org.jdbi.v3.core.statement.StatementContext;
import org.junit.jupiter.api.Test;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.mock;

class RoundDataMapperTest {
  RoundDataMapper roundDataMapper = new RoundDataMapper();
  Map<String, Object> data = new HashMap<>();

  @Test
  void map() throws SQLException {
    data.put("round_id", 1L);
    data.put("game_id", 15L);
    data.put("distance", 1000d);

    ResultSet resultSet = new MockResultSet(data);
    StatementContext ctx = mock(StatementContext.class);

    RoundDataEntity expectedOutput = new RoundDataEntity(1L, 15L, 1000d);

    assertThat(roundDataMapper.map(resultSet, ctx)).usingRecursiveComparison().isEqualTo(expectedOutput);

  }
}