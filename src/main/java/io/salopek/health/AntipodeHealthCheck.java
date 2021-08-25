package io.salopek.health;

import com.codahale.metrics.health.HealthCheck;
import io.salopek.db.DatabaseService;
import io.salopek.logging.LogBuilder;
import io.salopek.processor.GameProcessorImpl;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.inject.Inject;

public class AntipodeHealthCheck extends HealthCheck {

  private final DatabaseService databaseService;
  private static final Logger LOGGER = LoggerFactory.getLogger(AntipodeHealthCheck.class);

  @Inject
  public AntipodeHealthCheck(DatabaseService databaseService) {
    this.databaseService = databaseService;
  }

  @Override
  protected Result check() {
    LogBuilder lb = LogBuilder.get().log("Checking application health");
    if(databaseService.isConnected()) {
      lb.log("Service is healthy");
      LOGGER.info(lb.build());
      return Result.healthy();
    } else {
      lb.log("Service is healthy");
      LOGGER.error(lb.build());
      return Result.unhealthy("Cannot connect to database.");
    }
  }
}
