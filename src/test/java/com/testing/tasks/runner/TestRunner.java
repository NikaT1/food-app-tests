package com.testing.tasks.runner;

import org.junit.platform.suite.api.*;

import static io.cucumber.core.options.Constants.*;

@Suite
@IncludeEngines("cucumber")
@SelectClasspathResource("com.testing.tasks")
@ConfigurationParameters({
        @ConfigurationParameter(key = PLUGIN_PROPERTY_NAME, value = "pretty,io.qameta.allure.cucumber7jvm.AllureCucumber7Jvm"),
        @ConfigurationParameter(key = FILTER_TAGS_PROPERTY_NAME, value = "@all"),
        @ConfigurationParameter(key = GLUE_PROPERTY_NAME, value = "com.testing.tasks.steps")
})
public class TestRunner {
}
