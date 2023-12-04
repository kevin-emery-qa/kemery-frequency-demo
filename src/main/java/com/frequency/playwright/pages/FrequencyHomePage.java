package com.frequency.playwright.pages;

import com.microsoft.playwright.Page;
import com.microsoft.playwright.options.LoadState;
import com.microsoft.playwright.options.WaitForSelectorState;

public class FrequencyHomePage {
    private Page page;
    private Page.WaitForSelectorOptions isVisible;

    private static class Locators {
        // I noticed that more than one element on the page matched the href locators
        // Initially I found some locators to the correct links by specifying
        // that it needs a child element with .dropdown-toggle-text class
        // private static String topNavWorkflow = "a[href='/workflow'] .dropdown-toggle-text";
        // However that didn't work for the "Distribution" link which has no dropdown

        // Then I realized that I should explicitly specify that I do not want the mobile versions...
        private static String topNavWorkflow = "a[href='/workflow']:not(.mobile-nav)";
        private static String topNavDistribution = "a[href='/distribution']:not(.mobile-nav)";
        private static String topNavCopmany = "a[href='/about-us']:not(.mobile-nav)";
        private static String topNavSupport = "a[href*='support.frequency.com']:not(.mobile-nav)";
    }

    public FrequencyHomePage(Page page) {
        this.page = page;
        isVisible = new Page.WaitForSelectorOptions()
                .setState(WaitForSelectorState.VISIBLE)
                .setTimeout(10000);
    }

    public void validateHomePage() {
        page.navigate("http://frequency.com/");

        page.waitForLoadState(LoadState.NETWORKIDLE);
        validateTopMenu();
    }

    public void validateTopMenu() {
        page.waitForSelector(Locators.topNavCopmany, isVisible);
        page.waitForSelector(Locators.topNavDistribution, isVisible);
        page.waitForSelector(Locators.topNavSupport, isVisible);
        page.waitForSelector(Locators.topNavWorkflow, isVisible);
    }
}
