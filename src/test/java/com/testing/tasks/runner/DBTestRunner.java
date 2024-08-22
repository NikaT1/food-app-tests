package com.testing.tasks.runner;

import org.junit.platform.suite.api.*;

import static io.cucumber.core.options.Constants.FILTER_TAGS_PROPERTY_NAME;
import static io.cucumber.core.options.Constants.GLUE_PROPERTY_NAME;

@Suite
@IncludeEngines("cucumber")
@SelectClasspathResource("features")
@ConfigurationParameters({
        @ConfigurationParameter(key = FILTER_TAGS_PROPERTY_NAME, value = "@db-tests"),
        @ConfigurationParameter(key = GLUE_PROPERTY_NAME, value = "com.testing.tasks.steps")
})
public class DBTestRunner {
}
