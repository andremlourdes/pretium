package org.example.config;

import io.micrometer.core.instrument.Tag;
import io.micrometer.core.instrument.config.MeterFilter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.List;

@Configuration
public class ObservabilityConfig {

    private static final Logger log = LoggerFactory.getLogger(ObservabilityConfig.class);

    @Bean
    public MeterFilter meterFilter(@Value("${management.metrics.tags.application:pretium}") String appName) {
        log.info("Configurando observabilidade com application tag={}", appName);
        return MeterFilter.commonTags(List.of(Tag.of("application", appName)));
    }
}
