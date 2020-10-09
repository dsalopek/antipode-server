package io.salopek.health;

import com.codahale.metrics.health.HealthCheck;
import io.salopek.db.DatabaseService;

public class AntipodeHealthCheck extends HealthCheck {

  private final DatabaseService databaseService;

  public AntipodeHealthCheck(DatabaseService databaseService) {
    this.databaseService = databaseService;
  }

  @Override
  protected Result check() throws Exception {
    if(databaseService.isConnected()) {
      return Result.healthy();
    } else {
      return Result.unhealthy("Cannot connect to database.");
    }
  }
}
