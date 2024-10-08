package io.github.gabrmsouza.subscription.infrastructure.configuration;

import io.github.gabrmsouza.subscription.infrastructure.authentication.clientcredentials.RefreshClientCredentials;
import org.springframework.context.ApplicationListener;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.validation.beanvalidation.LocalValidatorFactoryBean;

import java.time.Clock;

@EnableScheduling
@Configuration(proxyBeanMethods = false)
@ComponentScan("io.github.gabrmsouza.subscription")
public class WebServerConfiguration {
    @Bean
    @Profile("!test-integration & !test-e2e")
    ApplicationListener<ContextRefreshedEvent> refreshClientCredentials(final RefreshClientCredentials clientCredentials) {
        return event -> clientCredentials.refresh();
    }

    @Bean
    Clock clock() {
        return Clock.systemUTC();
    }

    @Bean
    LocalValidatorFactoryBean localValidatorFactoryBean() {
        return new LocalValidatorFactoryBean();
    }
}
