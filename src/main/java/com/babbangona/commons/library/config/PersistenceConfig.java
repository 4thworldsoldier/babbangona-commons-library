package com.babbangona.commons.library.config;

import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@Configuration
@EnableJpaRepositories(basePackages = "com.babbangona.commons.library.repo")
@EntityScan(basePackages = "com.babbangona.commons.library.entities")
public class PersistenceConfig {
}
