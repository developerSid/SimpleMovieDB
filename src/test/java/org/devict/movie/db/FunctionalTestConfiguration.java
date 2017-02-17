package org.devict.movie.db;

import org.springframework.boot.test.autoconfigure.orm.jpa.AutoConfigureDataJpa;
import org.springframework.context.annotation.ComponentScan;

/**
 * Created by developerSid on 2/2/17.
 *
 * Configures the test JPA harness.
 */
@ComponentScan(basePackages = "org.devict.movie.db")
@AutoConfigureDataJpa
public class FunctionalTestConfiguration
{
}
