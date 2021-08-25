package io.salopek.di;

import io.salopek.processor.AuthenticationProcessor;
import io.salopek.processor.AuthenticationProcessorImpl;
import io.salopek.processor.GameProcessor;
import io.salopek.processor.GameProcessorImpl;
import io.salopek.util.DistanceCalculator;
import io.salopek.util.HaversineDistanceCalculator;
import org.glassfish.hk2.utilities.binding.AbstractBinder;

import javax.inject.Singleton;

public class ProcessorBinder extends AbstractBinder {
  @Override
  protected void configure() {
    bind(GameProcessorImpl.class).to(GameProcessor.class).in(Singleton.class);
    bind(AuthenticationProcessorImpl.class).to(AuthenticationProcessor.class).in(Singleton.class);
    bind(HaversineDistanceCalculator.class).to(DistanceCalculator.class).in(Singleton.class);
  }
}
