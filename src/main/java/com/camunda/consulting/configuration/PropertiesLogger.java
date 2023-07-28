package com.camunda.consulting.configuration;

import lombok.extern.slf4j.Slf4j;
import org.camunda.bpm.engine.ProcessEngine;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.task.TaskExecutionProperties;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.AbstractEnvironment;

import javax.annotation.PostConstruct;

@Configuration
@Slf4j
public class PropertiesLogger {

    private final ProcessEngine processEngine;
    private final TaskExecutionProperties taskExecutionProperties;

    @Value("${spring.profiles.active:dev}")
    private String activeProfile;

    @Value("${sleeping-time-in-millis:1000}")
    private Integer sleepingTimeInMillis;


    public PropertiesLogger(AbstractEnvironment environment, ProcessEngine processEngine, TaskExecutionProperties taskExecutionProperties) {
        this.processEngine = processEngine;
        this.taskExecutionProperties = taskExecutionProperties;
    }

    @PostConstruct
    public void logProperties(){
        log.info("*** APPLICATION PROPERTIES FOR LOAD TEST ***");
        log.info("Profile active: {}", activeProfile );
        log.info("Execution Time for Jobs: {}", sleepingTimeInMillis);
        log.info("HistoryLevel: {}", processEngine.getProcessEngineConfiguration().getHistory());
        log.info("--- JobExecutor Configuration:");
        log.info("--- JobExecutor.CoreThreads: {}", taskExecutionProperties.getPool().getCoreSize());
        log.info("--- JobExecutor.MaximumThreads: {}", taskExecutionProperties.getPool().getMaxSize());
        log.info("--- JobExecutor.QueueCapacity: {}", taskExecutionProperties.getPool().getQueueCapacity());
        log.info("********************************************");
    }
}
