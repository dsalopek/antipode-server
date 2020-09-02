package io.salopek.mapper.rowmapper;

import io.salopek.entity.UserDataEntity;
import org.jdbi.v3.core.mapper.RowMapper;
import org.jdbi.v3.core.statement.StatementContext;

import java.sql.ResultSet;
import java.sql.SQLException;

public class UserDataMapper implements RowMapper<UserDataEntity> {
  @Override
  public UserDataEntity map(ResultSet rs, StatementContext ctx) throws SQLException {
    return new UserDataEntity(rs.getLong("user_id"), rs.getString("user_name"), rs.getString("password"),
      rs.getString("access_token"));
  }
}
