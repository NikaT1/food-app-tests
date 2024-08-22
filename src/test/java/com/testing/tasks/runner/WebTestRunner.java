package com.testing.tasks.runner;

import static io.cucumber.core.options.Constants.*;

import org.junit.platform.suite.api.*;

@Suite
@IncludeEngines("cucumber")
@SelectClasspathResource("features")
@ConfigurationParameters({
        @ConfigurationParameter(key = FILTER_TAGS_PROPERTY_NAME, value = "@web-tests"),
        @ConfigurationParameter(key = GLUE_PROPERTY_NAME, value = "com.testing.tasks.steps")
})
public class WebTestRunner {
}
