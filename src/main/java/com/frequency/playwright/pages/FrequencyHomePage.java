package com.frequency.playwright.pages;

import com.microsoft.playwright.Page;
import com.microsoft.playwright.options.LoadState;
import com.microsoft.playwright.options.WaitForSelectorState;

public class FrequencyHomePage {
    private Page page;
    private Page.WaitForSelectorOptions isVisible;
    private StudioInfoPage studioInfoPage;

    public static class Locators {
        public static String topNavWorkflow = "a[href='/workflow']:not(.mobile-nav) .dropdown-toggle-text";
        private static String topNavDistribution = "a[href='/distribution']:not(.mobile-nav)";
        private static String topNavCompany = "a[href='/about-us']:not(.mobile-nav) .dropdown-toggle-text";
        private static String topNavSupport = "a[href*='support.frequency.com']:not(.mobile-nav) .dropdown-toggle-text";
    }

    public FrequencyHomePage(Page page) {
        this.page = page;
        isVisible = new Page.WaitForSelectorOptions()
                .setState(WaitForSelectorState.VISIBLE)
                .setTimeout(10000);
        // I like WaitForSelectorOptions because it lets us set whatever timeout we want for the performance threshold
        // I can set other variables equal to other conditions (visible, detached, etc) and different timeouts
        // see page.waitForSelector() calls in the validateTopMenu method for usage

    }

    public void validateHomePage() {
        page.navigate("http://frequency.com/");

        page.waitForLoadState(LoadState.NETWORKIDLE);
        validateTopMenuVisibility();
    }

    public void validateTopMenuVisibility() {
        page.waitForSelector(Locators.topNavCompany, isVisible);
        page.waitForSelector(Locators.topNavDistribution, isVisible);
        page.waitForSelector(Locators.topNavSupport, isVisible);
        page.waitForSelector(Locators.topNavWorkflow, isVisible);
    }

    public void validateWorkflowLinks() {
        studioInfoPage = new StudioInfoPage(page);

        studioInfoPage.loadAndValidate("Studio");
        studioInfoPage.loadAndValidate("Ingest");
        studioInfoPage.loadAndValidate("Connect");
        studioInfoPage.loadAndValidate("Manage");
        studioInfoPage.loadAndValidate("Schedule");
        studioInfoPage.loadAndValidate("Graphics");
        studioInfoPage.loadAndValidate("Analyze");
    }
}
