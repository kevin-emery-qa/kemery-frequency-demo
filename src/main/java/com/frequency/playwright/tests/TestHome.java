package com.frequency.playwright.tests;

import com.frequency.playwright.pages.FrequencyHomePage;
import com.microsoft.playwright.Playwright;
import com.microsoft.playwright.Browser;
import com.microsoft.playwright.BrowserContext;
import com.microsoft.playwright.Page;

import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeSuite;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

@Test
public class TestHome {
    static Playwright playwright;
    static Browser browser;
    static BrowserContext context;
    static Page page;

    FrequencyHomePage frequencyHomePage;

    @BeforeSuite
    void beforeSuite() {
        playwright = Playwright.create();
    }

    @BeforeTest
    void setupCompaniesTest() {
        browser = playwright.chromium().launch();
        context = browser.newContext();
        page = context.newPage();

        frequencyHomePage = new FrequencyHomePage(page);
    }

    @AfterTest
    void endTest() {
        page.close();
        context.close();
        browser.close();
    }

    @Test
    void testHomePage() {
        frequencyHomePage.validateHomePage();
    }

    @Test
    void testWorkflowMenu() {
        frequencyHomePage.validateWorkflowLinks();
    }
}
