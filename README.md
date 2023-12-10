# kemery-frequency-demo
Kevin Emery interview technical demo (Java/Playwright) for QA Release Manager position at frequency

## Requirements (osx)
XCode command-line Developer Tools (installed at the terminal with `xcode-select --install`)
IntelliJ IDEA CE or Ultimate - https://www.jetbrains.com/idea/
Add an ssh key from your system (e.g. `~.ssh/id_ed25519`) and add it to your github account in your account settings -> SSH

## Installation (osx)
1. Clone the repository - `git clone git@github.com:kevin-emery-qa/kemery-frequency-demo.git`
2. Import the Projcet - In IntelliJ IDEA, open the kemery-frequency-demo directory and trust the project
3. Edit Project Settings - Go to File menu -> Project Structure, and in the Project Structure dialog go to Project Settings -> Project
4. Choose an SDK - Choose an SDK already on your system for Java 17 or newer, or select "Download JDK".  Development was done with Amazon Coretto 17.0.9 but it should work on newer versions of Java
5. Select Language Level - Select Language Level 17 or newer and apply the changes
6. Confirm JDK - Keep an eye on the progress bars at the bottom of the IDE window, such as "Installing Amazon Coretto" and "Importing projects".  You can ignore warnings about the JDK until the installation is complete.
7. Confirm Maven imports - Once all progress bars have completed, confirm that you have a small "m" on the right side of Intellij, for Maven.  This is where any updated dependencies defined in pom.xml can be imported.  If it has not imported as a Maven project, try right-clicking on the pom.xml file and select "add as Maven project".
8. Confirm TestNG - Open `src/main/java/com/frequency/playwright/tests/TestHome.java` and scroll to the @Test annotations at the bottom of the file.  Right-click on one of the @Test annotations or their method's name.  You should see "Run" and "Debug" options available in the context menu.  If you don't have these, confirm that the org.testng dependency in pom.xml is successfully imported
9. Headed Run Configuration - Go to Run menu -> Edit Configurations and click "Edit configuration templates..." in the bottom-left corner of the window.  Select TestNG, and in the "Environment Variables" field, enter `PWDEBUG=1` (Playwright Debug) if you want to run it in headed mode
11. Launch tests - To run a single test in `com.frequency.playwright.tests`, right click the @Test annotation or its corresponding method's name (which always with `test`) and select the "Run" or "Debug" option for that method's name.  To run the entire suite of tests, right-click the "Tests" directory and select "Run" (be careful of headed mode here)

## Working in Java/Playwright
* When a test is running with the PWDEBUG environment variable set to 1, Playwright provides a Playwright Inspector window which can be interacted with while your test runs in Chromium.  In order to begin the test, click the "Play" button in the Inspector.
* You may notice that the test finishes and closes quickly.  To pause it, add a breakpoint in your IDE on the page object methods that are called by the tests and then select the "Debug" option to run your test instead of the "Run option".  For example, you could add a breakpoint on line 33 of `FrequencyHomePage.java` at the last line of the validateHomePage() method by clicking on the line number to the left of the call to `validateTopMenuVisibility();`
* Running a test with the Debug menu option instead of the Run menu option will allow you to play around with the Playwright Inspector while the test execution is paused at the breakpoint.
* The Playwright Inspector is sometimes more useful to use when writing modern CSS element locators because like Playwright itself, it respects the newer additions to the CSS selector language which Chrome does not support.  For example, Chrome will match zero results in the inspector if you use `":has(<descendent element>)"` or ":has-text('text')", but Playwright respects these newer addition to the CSS selector language.
* The Playwright Inspector can also be useful as a visual tool to see what Playwright is doing at each step.
