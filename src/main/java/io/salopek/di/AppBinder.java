package io.salopek.di;

import io.dropwizard.Configuration;
import io.dropwizard.lifecycle.setup.LifecycleEnvironment;
import io.dropwizard.setup.Environment;
import io.salopek.AppConfiguration;
import org.glassfish.hk2.utilities.binding.AbstractBinder;

public class AppBinder extends AbstractBinder {
  private final AppConfiguration configuration;
  private final Environment environment;

  public AppBinder(AppConfiguration configuration, Environment environment) {
    this.configuration = configuration;
    this.environment = environment;
  }

  @Override
  protected void configure() {
    bind(configuration).to(AppConfiguration.class);
    bind(environment).to(Environment.class);
    bind(environment.lifecycle()).to(LifecycleEnvironment.class);
  }
}
