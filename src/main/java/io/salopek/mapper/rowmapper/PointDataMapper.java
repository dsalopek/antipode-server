package io.salopek.mapper.rowmapper;

import io.salopek.constant.PointType;
import io.salopek.entity.PointEntity;
import org.jdbi.v3.core.mapper.RowMapper;
import org.jdbi.v3.core.statement.StatementContext;

import java.sql.ResultSet;
import java.sql.SQLException;

public class PointDataMapper implements RowMapper<PointEntity> {

  @Override
  public PointEntity map(ResultSet rs, StatementContext ctx) throws SQLException {
    return new PointEntity(rs.getLong("point_id"), rs.getLong("round_id"), PointType.valueOf(rs.getString("type")),
      rs.getDouble("latitude"),
      rs.getDouble("longitude"));
  }
}