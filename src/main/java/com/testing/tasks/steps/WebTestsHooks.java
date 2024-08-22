package com.testing.tasks.steps;

import com.testing.tasks.managers.InitManager;
import io.cucumber.java.After;
import io.cucumber.java.Before;

public class WebTestsHooks {
    @Before("@web-tests")
    public void initFramework() {
        InitManager.initFramework();
    }

    @After("@web-tests")
    public void quitFramework() {
        InitManager.quitFramework();
    }
}
