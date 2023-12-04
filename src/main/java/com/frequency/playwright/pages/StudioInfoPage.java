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

    private static class Locators {
        private static String workflowSubmenuByText = "nav.workflow.dropdown-list a:has-text('";
        private static String workflowSubmenuByTextClose = "')";
    }

    public StudioInfoPage(Page page) {
        this.page = page;
        frequencyHomePage = new FrequencyHomePage(page);
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

        frequencyHomePage.validateTopMenu();
        page.hover(FrequencyHomePage.Locators.topNavWorkflow);
        String pageLink = Locators.workflowSubmenuByText + workflowMenuOption + Locators.workflowSubmenuByTextClose;
        page.waitForSelector(pageLink, isVisible);
        page.locator(pageLink).click();
        page.waitForSelector(pageLink, isHidden);
        page.waitForTimeout(5000);
    }
}
