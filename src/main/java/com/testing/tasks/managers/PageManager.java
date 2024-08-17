package com.testing.tasks.managers;

import com.testing.tasks.pages.FoodPage;
public class PageManager {

    private FoodPage foodPage;

    private static PageManager INSTANCE;

    private PageManager() {
    }

    public static PageManager getInstance() {
        if (INSTANCE == null) {
            INSTANCE = new PageManager();
        }
        return INSTANCE;
    }

    public FoodPage getHomePage() {
        if (foodPage == null) {
            foodPage = new FoodPage();
        }
        return foodPage;
    }

}
