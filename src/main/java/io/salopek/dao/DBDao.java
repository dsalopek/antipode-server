package io.salopek.dao;

import org.jdbi.v3.sqlobject.statement.SqlQuery;

public interface DBDao {
  @SqlQuery("SELECT 1")
  boolean isConnected();
}
