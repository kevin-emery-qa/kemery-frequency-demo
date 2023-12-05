package com.frequency.playwright.pages;

import com.microsoft.playwright.Page;
import com.microsoft.playwright.options.LoadState;
import com.microsoft.playwright.options.WaitForSelectorState;

public class StudioInfoPage {
    private Page page;

    // allows me to access methods and locators defined in annother page object class file
    private FrequencyHomePage frequencyHomePage;
    private Page.WaitForSelectorOptions isVisible;
    private Page.WaitForSelectorOptions isHidden;

    public static class Locators {
        public static String workflowSubmenuByText = "nav.workflow.dropdown-list a:has-text('";
        public static String workflowSubmenuByTextClose = "')";

        // at first glance, this locator looks brittle, referencing too many classes.  The rationale is that
        // this is unique among the 5 matches to href='/contact' and it no confusing wildcards, even though it is long.
        private static String requestDemoButton = "a[href='/contact'] div.button-gradient-light div.contactustext";
    }

    public StudioInfoPage(Page page) {
        this.page = page;
        isVisible = new Page.WaitForSelectorOptions()
                .setState(WaitForSelectorState.VISIBLE)
                .setTimeout(10000);
        isHidden = new Page.WaitForSelectorOptions()
                .setState(WaitForSelectorState.HIDDEN)
                .setTimeout(2000);
    }

    public void loadAndValidate(String workflowMenuOption) {
        page.navigate("http://frequency.com/");
        page.waitForLoadState(LoadState.NETWORKIDLE);

        frequencyHomePage = new FrequencyHomePage(page);
        frequencyHomePage.validateTopMenuVisibility();
        page.hover(FrequencyHomePage.Locators.topNavWorkflow);
        String pageLink = Locators.workflowSubmenuByText + workflowMenuOption + Locators.workflowSubmenuByTextClose;
        page.waitForSelector(pageLink, isVisible);
        page.locator(pageLink).click();
        page.waitForSelector(pageLink, isHidden);

        page.waitForSelector(Locators.requestDemoButton, isVisible);
    }

    public void openDemoContact() {
        page.locator(Locators.requestDemoButton).scrollIntoViewIfNeeded();
        page.locator(Locators.requestDemoButton).click();
        page.waitForTimeout(1000);
    }
}
