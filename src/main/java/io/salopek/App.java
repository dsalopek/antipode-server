package io.salopek;

import io.dropwizard.Application;
import io.dropwizard.auth.AuthDynamicFeature;
import io.dropwizard.auth.AuthValueFactoryProvider;
import io.dropwizard.auth.oauth.OAuthCredentialAuthFilter;
import io.dropwizard.configuration.EnvironmentVariableSubstitutor;
import io.dropwizard.configuration.SubstitutingSourceProvider;
import io.dropwizard.jdbi3.JdbiFactory;
import io.dropwizard.lifecycle.setup.LifecycleEnvironment;
import io.dropwizard.setup.Bootstrap;
import io.dropwizard.setup.Environment;
import io.salopek.dao.DBDao;
import io.salopek.dao.GameDataDAO;
import io.salopek.dao.GameIdDAO;
import io.salopek.dao.HighScoreDAO;
import io.salopek.dao.PointDataDAO;
import io.salopek.dao.RoundDataDAO;
import io.salopek.dao.UserDataDAO;
import io.salopek.db.DatabaseService;
import io.salopek.db.DatabaseServiceImpl;
import io.salopek.di.AppBinder;
import io.salopek.di.AuthFeatureFactory;
import io.salopek.di.DatabaseBinder;
import io.salopek.di.ProcessorBinder;
import io.salopek.exception.JerseyViolationExceptionMapper;
import io.salopek.exception.JsonProcessingExceptionMapper;
import io.salopek.filter.AntipodeFilter;
import io.salopek.model.UserData;
import io.salopek.processor.AuthenticationProcessor;
import io.salopek.processor.AuthenticationProcessorImpl;
import io.salopek.processor.GameProcessor;
import io.salopek.processor.GameProcessorImpl;
import io.salopek.resource.AuthenticationResource;
import io.salopek.resource.GameResource;
import io.salopek.security.CoreAuthenticator;
import io.salopek.security.CoreAuthorizer;
import io.salopek.security.UnauthorizedHandler;
import io.salopek.util.DistanceCalculator;
import io.salopek.util.HaversineDistanceCalculator;
import org.checkerframework.checker.units.qual.A;
import org.glassfish.hk2.utilities.binding.AbstractBinder;
import org.jdbi.v3.core.Jdbi;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

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
    bootstrap.setConfigurationSourceProvider(new SubstitutingSourceProvider(
      bootstrap.getConfigurationSourceProvider(),
      new EnvironmentVariableSubstitutor(false)));
  }

  @Override
  public void run(final AppConfiguration configuration,
    final Environment environment) {

    registerJerseyComponents(environment, configuration);

    environment.servlets().addFilter("InboundRequestFilter", new AntipodeFilter())
      .addMappingForUrlPatterns(EnumSet.of(DispatcherType.REQUEST), true, "/*");
  }

  private void registerJerseyComponents(Environment environment, AppConfiguration configuration) {
    final JdbiFactory factory = new JdbiFactory();
    final Jdbi jdbi = factory.build(environment, configuration.getDataSourceFactory(), "database");

    environment.jersey().register(new AppBinder(configuration, environment));
    environment.jersey().register(new DatabaseBinder(jdbi));
    environment.jersey().register(new ProcessorBinder());
    environment.jersey().register(AuthenticationResource.class);
    environment.jersey().register(GameResource.class);
    environment.jersey().register(JerseyViolationExceptionMapper.class);
    environment.jersey().register(JsonProcessingExceptionMapper.class);
    environment.jersey().register(AuthFeatureFactory.get());
    environment.jersey().register(new AuthValueFactoryProvider.Binder<>(UserData.class));
  }
}
