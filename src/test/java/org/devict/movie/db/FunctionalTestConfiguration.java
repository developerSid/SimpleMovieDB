package org.devict.movie.db;

import org.junit.Ignore;
import org.springframework.boot.test.autoconfigure.orm.jpa.AutoConfigureDataJpa;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.web.client.RestTemplate;

/**
 * Created by developerSid on 2/2/17.
 *
 * Configures the test JPA harness.
 */
@Ignore
@ComponentScan(basePackages = "org.devict.movie.db")
@AutoConfigureDataJpa
public class FunctionalTestConfiguration
{
   @Bean
   public RestTemplate restTemplate()
   {
      return new RestTemplate();
   }
}
