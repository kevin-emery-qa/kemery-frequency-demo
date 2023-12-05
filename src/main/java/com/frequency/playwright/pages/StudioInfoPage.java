package com.frequency.playwright.pages;

import com.microsoft.playwright.FrameLocator;
import com.microsoft.playwright.Page;
import com.microsoft.playwright.options.LoadState;
import com.microsoft.playwright.options.WaitForSelectorState;
import org.testng.Assert;

public class StudioInfoPage {
    private Page page;

    // allows us to access methods and locators defined in another page object class file
    private FrequencyHomePage frequencyHomePage;
    private Page.WaitForSelectorOptions isVisible;
    private Page.WaitForSelectorOptions isHidden;

    public static class Locators {
        public static String workflowSubmenuByText = "nav.workflow.dropdown-list a:has-text('";
        public static String workflowSubmenuByTextClose = "')";

        // at first glance, this locator looks brittle, referencing too many classes.  The rationale is that
        // this is unique among the 5 matches to href='/contact' and it no confusing wildcards, even though it is long.
        private static String requestDemoButton = "a[href='/contact'] div.button-gradient-light div.contactustext";

        private static String contactFrame = "iframe#hs-form-iframe-0";
        public static class ContactFrame {
            // Populating a page object with lots of locators for static elements like this label causes tests that
            // have little meaningful chance to fail and detect a bug
            // However, it can be useful to examine the locators of static elements and look deeper into the page
            private static String nameLabel = "[id*='label-firstname'] span:has-text('Name')";

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

            private static String requiredWarningLabel = "label >> text='Please complete all required fields.'";

            // if the company domain/DevOps allows it, we could configure a java pop3/smtp connection to check the mail
            // and delete the test mail each night to monitor production uptime of the incoming marketing e-mail form
            // and this would be a more meaningful test than checking lots of static page elements
        }
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

    public void openContactForm() {
        page.locator(Locators.requestDemoButton).scrollIntoViewIfNeeded();
        page.locator(Locators.requestDemoButton).click();
        page.waitForLoadState(LoadState.NETWORKIDLE);
        Assert.assertTrue(page.frameLocator(Locators.contactFrame).locator(Locators.ContactFrame.nameLabel).isVisible());
        Assert.assertTrue(page.frameLocator(Locators.contactFrame).locator(Locators.ContactFrame.linearChannelsYes).isVisible());
        Assert.assertTrue(page.frameLocator(Locators.contactFrame).locator(Locators.ContactFrame.sendButton).isVisible());
    }

    public void validateContactForm() {
        FrameLocator contactFrame = page.frameLocator(Locators.contactFrame);

        Assert.assertFalse(isFrameElementVisible(Locators.ContactFrame.requiredWarningLabel));
        contactFrame.locator(Locators.ContactFrame.nameField).clear();
        contactFrame.locator(Locators.ContactFrame.sendButton).click();
        Assert.assertTrue(isFrameElementVisible(Locators.ContactFrame.requiredWarningLabel));

        fillFrameElement(Locators.ContactFrame.nameField, "Frequency Tester");
        fillFrameElement(Locators.ContactFrame.companyField, "TestCompany, LLC");
        fillFrameElement(Locators.ContactFrame.nameField, "8675309");

        Assert.assertTrue(isFrameElementVisible(Locators.ContactFrame.emailField));
        Assert.assertTrue(isFrameElementVisible(Locators.ContactFrame.phoneField));
        Assert.assertTrue(isFrameElementVisible(Locators.ContactFrame.referralSelectList));
        Assert.assertTrue(isFrameElementVisible(Locators.ContactFrame.linearChannelsYes));
        Assert.assertTrue(isFrameElementVisible(Locators.ContactFrame.interestedField));
        Assert.assertTrue(isFrameElementVisible(Locators.ContactFrame.newsletterCheckbox));
    }

    private boolean isFrameElementVisible(String iframeLocator) {
        return page.frameLocator(Locators.contactFrame).locator(iframeLocator).isVisible();
    }

    private void fillFrameElement(String iframeLocator, String text) {
        page.frameLocator(Locators.contactFrame).locator(iframeLocator).fill(text);
    }
}
