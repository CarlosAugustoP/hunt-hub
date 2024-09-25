package com.groupseven.hunthub.steps;

import io.cucumber.spring.CucumberContextConfiguration;
import org.springframework.boot.test.context.SpringBootTest;

@CucumberContextConfiguration
@ContextConfiguration(classes = {TaskStepDefinitions.class}, loader = AnnotationConfigContextLoader.class)
public class CucumberSpringConfiguration {
    // Classe de configuração de contexto para o Cucumber
}
