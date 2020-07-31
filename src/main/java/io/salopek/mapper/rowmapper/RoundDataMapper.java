package io.salopek.mapper.rowmapper;

import io.salopek.entity.RoundDataEntity;
import org.jdbi.v3.core.mapper.RowMapper;
import org.jdbi.v3.core.statement.StatementContext;

import java.sql.ResultSet;
import java.sql.SQLException;

public class RoundDataMapper implements RowMapper<RoundDataEntity> {

  @Override
  public RoundDataEntity map(ResultSet rs, StatementContext ctx) throws SQLException {
    return new RoundDataEntity(rs.getLong("round_id"), rs.getLong("game_id"), rs.getDouble("distance"));
  }
}