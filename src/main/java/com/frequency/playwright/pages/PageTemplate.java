package com.frequency.playwright.pages;

import com.microsoft.playwright.Page;
import com.microsoft.playwright.options.LoadState;
import com.microsoft.playwright.options.WaitForSelectorState;

public class PageTemplate {
    private Page page;
    private Page.WaitForSelectorOptions isVisible;

    private static class Locators {
        private static String someElement = "";
    }

    public PageTemplate(Page page) {
        this.page = page;
        isVisible = new Page.WaitForSelectorOptions()
                .setState(WaitForSelectorState.VISIBLE)
                .setTimeout(10000);
    }

    public void validateSomething() {
        page.navigate("http://frequency.com/");

        page.waitForLoadState(LoadState.NETWORKIDLE);
    }
}
