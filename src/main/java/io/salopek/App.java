package io.salopek;

import io.dropwizard.Application;
import io.dropwizard.auth.AuthValueFactoryProvider;
import io.dropwizard.configuration.EnvironmentVariableSubstitutor;
import io.dropwizard.configuration.SubstitutingSourceProvider;
import io.dropwizard.setup.Bootstrap;
import io.dropwizard.setup.Environment;
import io.salopek.di.AuthFeatureFactory;
import io.salopek.di.DependencyInjectionBundle;
import io.salopek.exception.JerseyViolationExceptionMapper;
import io.salopek.exception.JsonProcessingExceptionMapper;
import io.salopek.filter.AntipodeFilter;
import io.salopek.model.UserData;

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
  public void run(final AppConfiguration configuration, final Environment environment) {

    registerJerseyComponents(environment, configuration);

    environment.servlets().addFilter("InboundRequestFilter", new AntipodeFilter())
      .addMappingForUrlPatterns(EnumSet.of(DispatcherType.REQUEST), true, "/*");
  }

  private void registerJerseyComponents(Environment environment, AppConfiguration configuration) {
    final DependencyInjectionBundle dependencyInjectionBundle = new DependencyInjectionBundle();
    dependencyInjectionBundle.run(configuration, environment);
    environment.jersey().register(JerseyViolationExceptionMapper.class);
    environment.jersey().register(JsonProcessingExceptionMapper.class);
    environment.jersey().register(AuthFeatureFactory.get());
    environment.jersey().register(new AuthValueFactoryProvider.Binder<>(UserData.class));
  }
}
