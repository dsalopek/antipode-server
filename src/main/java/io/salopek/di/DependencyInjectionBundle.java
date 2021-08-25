package io.salopek.di;

import io.dropwizard.ConfiguredBundle;
import io.dropwizard.jdbi3.JdbiFactory;
import io.dropwizard.setup.Environment;
import io.salopek.AppConfiguration;
import io.salopek.resource.AuthenticationResource;
import io.salopek.resource.GameResource;
import org.jdbi.v3.core.Jdbi;

public class DependencyInjectionBundle implements ConfiguredBundle<AppConfiguration> {

  @Override
  public void run(AppConfiguration configuration, Environment environment) {
    final JdbiFactory factory = new JdbiFactory();
    final Jdbi jdbi = factory.build(environment, configuration.getDataSourceFactory(), "database");
    environment.jersey().register(new AppBinder(configuration, environment));
    environment.jersey().register(new DatabaseBinder(jdbi));
    environment.jersey().register(new ProcessorBinder());
    environment.jersey().register(AuthenticationResource.class);
    environment.jersey().register(GameResource.class);
  }
}
