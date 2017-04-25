package com.akhalikov.backend.jetty.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

@Configuration
@Import(JettyConfig.class)
public class AppConfig {
}
