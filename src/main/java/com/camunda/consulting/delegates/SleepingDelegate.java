package com.camunda.consulting.delegates;

import lombok.extern.slf4j.Slf4j;
import org.camunda.bpm.engine.delegate.DelegateExecution;
import org.camunda.bpm.engine.delegate.JavaDelegate;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
@Slf4j
public class SleepingDelegate implements JavaDelegate {

    @Value("${sleeping-time-in-millis:1000}")
    private Integer sleepingTime;

    public void execute(DelegateExecution delegateExecution) throws Exception {
        log.info("Going to sleeeeeeep for {}ms", sleepingTime);
        Thread.sleep(sleepingTime);
    }
}
