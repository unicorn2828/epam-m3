package com.epam.test.config;

import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;

@Configuration
@EnableAutoConfiguration
@EntityScan("com.epam.esm")
@ComponentScan("com.epam.esm")
@PropertySource("classpath:test.properties")
public class RepositoryTestConfig {
}
