package io.salopek.mapper.rowmapper;

import io.salopek.constant.PointType;
import io.salopek.entity.PointEntity;
import io.salopek.util.MockResultSet;
import org.jdbi.v3.core.statement.StatementContext;
import org.junit.jupiter.api.Test;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.mock;

class PointDataMapperTest {
  PointDataMapper pointDataMapper = new PointDataMapper();
  Map<String, Object> data = new HashMap<>();

  @Test
  void map() throws SQLException {
    data.put("point_id", 15L);
    data.put("round_id", 14L);
    data.put("type", "ORIGIN");
    data.put("latitude", 55d);
    data.put("longitude", 100d);

    ResultSet resultSet = new MockResultSet(data);
    StatementContext ctx = mock(StatementContext.class);

    PointEntity expectedOutput = new PointEntity(15L, 14L, PointType.ORIGIN, 55d, 100d);

    assertThat(pointDataMapper.map(resultSet, ctx)).usingRecursiveComparison().isEqualTo(expectedOutput);

  }
}