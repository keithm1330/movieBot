package com.movie.moviebot;

import com.slack.api.bolt.App;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class SlackApp {

  @Bean
  public App initSlackApp() {
    App app = new App();
    return app;
  }
}
