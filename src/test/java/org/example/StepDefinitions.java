package org.example;

import io.cucumber.java.en.*;
import io.github.bonigarcia.wdm.WebDriverManager;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;

import static org.junit.jupiter.api.Assertions.*;

public class StepDefinitions {
    private WebDriver driver;
    @Given("an example scenario")
    public void anExampleScenario() {
    }

    @When("all step definitions are implemented")
    public void allStepDefinitionsAreImplemented() {
    }

    @Then("the scenario passes")
    public void theScenarioPasses() {
    }


    @Given("I launch the Chrome browser")
    public void i_launch_the_chrome_browser() {
        // Set up ChromeDriver using WebDriverManager
        WebDriverManager.chromedriver().setup();
        driver = new ChromeDriver();
        driver.manage().window().maximize(); // Optional: Maximize the browser window
    }

    @When("I navigate to {string}")
    public void i_navigate_to(String url) {
        driver.get(url);

    }

    @Then("the page title should be {string}")
    public void the_page_title_should_be(String expectedTitle) {
        String actualTitle = driver.getTitle();
        assertEquals(expectedTitle, actualTitle, "Page title does not match!");
        driver.quit(); // Close the browser after the test
    }

}
