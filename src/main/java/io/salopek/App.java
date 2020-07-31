package io.salopek;

import io.dropwizard.Application;
import io.dropwizard.jdbi3.JdbiFactory;
import io.dropwizard.lifecycle.setup.LifecycleEnvironment;
import io.dropwizard.setup.Bootstrap;
import io.dropwizard.setup.Environment;
import io.salopek.dao.GameDataDAO;
import io.salopek.dao.GameIdDAO;
import io.salopek.dao.PointDataDAO;
import io.salopek.dao.RoundDataDAO;
import io.salopek.db.DatabaseService;
import io.salopek.db.DatabaseServiceImpl;
import io.salopek.filter.AntipodeFilter;
import io.salopek.processor.GameProcessor;
import io.salopek.resource.GameResource;
import io.salopek.util.DistanceCalculator;
import io.salopek.util.HaversineDistanceCalculator;
import org.glassfish.hk2.utilities.binding.AbstractBinder;
import org.jdbi.v3.core.Jdbi;

import javax.inject.Singleton;
import javax.servlet.DispatcherType;
import java.util.EnumSet;

public class App extends Application<AppConfiguration> {
  public static void main(final String[] args) throws Exception {
    new App().run(args);
  }

  @Override
  public String getName() {
    return "App-Antipode";
  }

  @Override
  public void initialize(final Bootstrap<AppConfiguration> bootstrap) {
    // TODO: application initialization
  }

  @Override
  public void run(final AppConfiguration configuration,
    final Environment environment) {

    final JdbiFactory factory = new JdbiFactory();
    final Jdbi jdbi = factory.build(environment, configuration.getDataSourceFactory(), "mysql");

//    test
//    test2
    
    GameDataDAO gameDataDAO = jdbi.onDemand(GameDataDAO.class);
    RoundDataDAO roundDataDAO = jdbi.onDemand(RoundDataDAO.class);
    PointDataDAO pointDataDAO = jdbi.onDemand(PointDataDAO.class);
    GameIdDAO gameIdDAO = jdbi.onDemand(GameIdDAO.class);

    environment.jersey().register(new AbstractBinder() {
      @Override
      protected void configure() {
        bind(configuration).to(AppConfiguration.class);
        bind(environment).to(Environment.class);
        bind(environment.lifecycle()).to(LifecycleEnvironment.class);
        bind(jdbi).to(Jdbi.class);
        bind(gameDataDAO).to(GameDataDAO.class);
        bind(roundDataDAO).to(RoundDataDAO.class);
        bind(pointDataDAO).to(PointDataDAO.class);
        bind(gameIdDAO).to(GameIdDAO.class);
        bind(GameProcessor.class).to(GameProcessor.class).in(Singleton.class);
        bind(HaversineDistanceCalculator.class).to(DistanceCalculator.class).in(Singleton.class);
        bind(DatabaseServiceImpl.class).to(DatabaseService.class).in(Singleton.class);
      }
    });

    environment.jersey().register(GameResource.class);

    environment.servlets().addFilter("InboundRequestFilter", new AntipodeFilter())
      .addMappingForUrlPatterns(EnumSet.of(DispatcherType.REQUEST), true, "/*");
  }
}
