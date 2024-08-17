package com.testing.tasks.managers;

import com.testing.tasks.pages.HomePage;
public class PageManager {

    private HomePage homePage;

    private static PageManager INSTANCE;

    private PageManager() {
    }

    public static PageManager getInstance() {
        if (INSTANCE == null) {
            INSTANCE = new PageManager();
        }
        return INSTANCE;
    }

    public HomePage getHomePage() {
        if (homePage == null) {
            homePage = new HomePage();
        }
        return homePage;
    }

}
