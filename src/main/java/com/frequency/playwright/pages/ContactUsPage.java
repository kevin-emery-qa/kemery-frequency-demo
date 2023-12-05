package com.frequency.playwright.pages;

import com.microsoft.playwright.Page;
import com.microsoft.playwright.options.LoadState;
import com.microsoft.playwright.options.WaitForSelectorState;

public class ContactUsPage {
    private Page page;

    // allows us to access methods and locators defined in another page object class file
    private FrequencyHomePage frequencyHomePage;
    private Page.WaitForSelectorOptions isVisible;
    private Page.WaitForSelectorOptions isHidden;

    public static class Locators {
        public static String workflowSubmenuByText = "nav.workflow.dropdown-list a:has-text('";
        public static String workflowSubmenuByTextClose = "')";

        // at first glance, this locator looks brittle, referencing too many classes.  The rationale is that
        // this is unique among the 5 matches to href='/contact' and has no confusing wildcards, even though it is long.
        private static String requestDemoButton = "a[href='/contact'] div.button-gradient-light div.contactustext";

        // Populating a page object with lots of locators for static elements like this field label causes tests that
        // have little meaningful chance to fail and detect a bug
        // However, it can be useful to examine the locators of static elements and look deeper into the page
        private static String nameLabel = "[id*='label-firstname'] span >> text = 'Name'";

        // selectors for the text fields (to fill out the form for a functional test)
        private static String nameField = "input[id^='firstname'][required]";
        private static String companyField = "input[id^='company'][required]";
        private static String emailField = "input[id^='email'][required]";
        private static String phoneField = "input[id^='phone']:not([required])";
        private static String referralSelectList = "select[id^='form_referral']";
        private static String linearChannelsYes = "input[id^='linear_channels'][value='Yes']";
        private static String linearChannelsNo = "input[id^='linear_channels'][value='No']";
        private static String interestedField = "textarea[id^='interested_in'][required]";
        private static String newsletterCheckbox = "input[id^='newsletter']:not([required])";

        private static String sendButton = "input[type='submit'][value='Send']";

        // if the company domain allows it, we could configure a java pop3/smtp connection to check the mail
        // and delete the test mail each night to monitor production uptime of the incoming marketing e-mail form
        // and this would be a more meaningful test than checking lots of static page elements
    }

    public ContactUsPage(Page page) {
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

    public void openContactForm() {
        page.locator(Locators.requestDemoButton).scrollIntoViewIfNeeded();
        page.locator(Locators.requestDemoButton).click();
        page.waitForLoadState(LoadState.NETWORKIDLE);
        page.waitForSelector(Locators.nameLabel, isVisible);
        page.waitForSelector(Locators.linearChannelsYes, isVisible);
        page.waitForSelector(Locators.sendButton, isVisible);
    }

    public void validateContactForm() {
    }
}
