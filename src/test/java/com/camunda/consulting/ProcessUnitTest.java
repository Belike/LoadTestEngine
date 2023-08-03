package com.camunda.consulting;

import com.camunda.consulting.delegates.SleepingDelegate;
import com.camunda.consulting.util.ProcessConstants;
import org.camunda.bpm.engine.ProcessEngine;
import org.camunda.bpm.engine.runtime.ProcessInstance;
import org.camunda.bpm.engine.test.Deployment;
import org.camunda.bpm.engine.test.mock.Mocks;
import org.camunda.community.process_test_coverage.junit5.platform7.ProcessEngineCoverageExtension;
import org.camunda.community.process_test_coverage.spring_test.platform7.ProcessEngineCoverageConfiguration;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.TestPropertySource;

import static org.camunda.bpm.engine.test.assertions.bpmn.BpmnAwareTests.*;


/**
 * Test case starting an in-memory database-backed Process Engine.
 */
@ExtendWith(ProcessEngineCoverageExtension.class)
@TestPropertySource(properties = {
        "sleepingTime =100"
})
public class ProcessUnitTest {

  @Autowired
  private ProcessEngine processEngine;

  @Mock
  SleepingDelegate sleepingDelegateMock;

  @BeforeEach
  public void setup() {
    init(processEngine);
    MockitoAnnotations.openMocks(this);
    Mocks.register("sleepingDelegate", sleepingDelegateMock);
  }

  @Test
  @Deployment(resources = "SingleTaskAsynch.bpmn") // only required for process test coverage
  public void testHappyPath() {
    // Drive the process by API and assert correct behavior by camunda-bpm-assert

    ProcessInstance processInstance = processEngine().getRuntimeService()
        .startProcessInstanceByKey(ProcessConstants.PROCESS_DEFINITION_KEY);

    assertThat(processInstance).isWaitingAt("ServiceTask_Async");
    execute(job());

    assertThat(processInstance).isEnded().hasPassed("ServiceTask_Async");

  }

}
