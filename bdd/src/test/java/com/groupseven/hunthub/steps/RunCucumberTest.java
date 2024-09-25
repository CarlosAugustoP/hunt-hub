package com.groupseven.hunthub.steps;

import io.cucumber.junit.platform.engine.Cucumber;
import org.junit.platform.suite.api.ConfigurationParameter;
import io.cucumber.junit.platform.engine.Constants;

@Cucumber
@ConfigurationParameter(key = Constants.PLUGIN_PROPERTY_NAME, value = "pretty")
@ConfigurationParameter(key = Constants.GLUE_PROPERTY_NAME, value = "com.groupseven.hunthub.steps")
public class RunCucumberTest {
}
