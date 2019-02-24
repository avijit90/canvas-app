package com.canvas.app.config;

import com.canvas.app.service.IOrchestrationService;
import com.canvas.app.service.OrchestrationServiceImpl;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class AppConfig {

    @Bean
    public IOrchestrationService orchestrationService() {
        return new OrchestrationServiceImpl();
    }

}
