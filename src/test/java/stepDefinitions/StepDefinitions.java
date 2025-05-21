package stepDefinitions;

import io.cucumber.java.en.*;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.WebDriver;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import javax.imageio.ImageIO;
import java.io.File;
import org.apache.commons.io.FileUtils;
import org.openqa.selenium.JavascriptExecutor;
import ru.yandex.qatools.ashot.AShot;
import ru.yandex.qatools.ashot.Screenshot;
import ru.yandex.qatools.ashot.shooting.ShootingStrategies;
import static org.testng.Assert.assertEquals;

public class StepDefinitions {
    private static final Logger logger = LogManager.getLogger(StepDefinitions.class);
    private WebDriver driver = TestContext.getDriver();
    private Connection connection;

    private TestContext context;
    public StepDefinitions(TestContext context) {
        this.context = context;
    }

    @Given("an example scenario")
    public void anExampleScenario() {
        logger.info("Example scenario started");
    }

    @When("all step definitions are implemented")
    public void allStepDefinitionsAreImplemented() {
        logger.info("All step definitions implemented");
    }

    @Then("the scenario passes")
    public void theScenarioPasses() {
        logger.info("Scenario passed");
    }

    @Given("I launch the Chrome browser")
    public void i_launch_the_chrome_browser() {
        logger.info("Launching Chrome browser");
        // Driver is now managed by TestContext
    }

    @When("I navigate to {string}")
    public void i_navigate_to(String url) {
        logger.info("Navigating to {}", url);
        driver.get(url);
    }

    @Then("the page title should be {string}")
    public void the_page_title_should_be(String expectedTitle) {
        String actualTitle = driver.getTitle();
        logger.info("Verifying page title: expected '{}', actual '{}'", expectedTitle, actualTitle);
        assertEquals(actualTitle, expectedTitle, "Page title does not match!");
    }

    @And("take screenshot of the Entire Page")
    public void takeScreenshotOfTheEntirePage() {
        logger.info("Taking full-page screenshot");
        try {
            JavascriptExecutor js = (JavascriptExecutor) driver;
            long lastHeight = (Long) js.executeScript("return document.body.scrollHeight");
            while (true) {
                js.executeScript("window.scrollTo(0, document.body.scrollHeight);");
                Thread.sleep(3000);
                long newHeight = (Long) js.executeScript("return document.body.scrollHeight");
                if (newHeight == lastHeight) {
                    break;
                }
                lastHeight = newHeight;
            }
            js.executeScript("window.scrollTo(0, 2000);");
            Thread.sleep(3000);

            Screenshot screenshot = new AShot()
                    .shootingStrategy(ShootingStrategies.viewportPasting(ShootingStrategies.scaling(1.0f), 3000))
                    .takeScreenshot(driver);

            File tempFile = new File("tempFullPageScreenshot.png");
            ImageIO.write(screenshot.getImage(), "PNG", tempFile);
            File finalFile = new File("fullPageScreenshot.png");
            FileUtils.copyFile(tempFile, finalFile);
            logger.info("Full-page screenshot saved as fullPageScreenshot.png");
        } catch (Exception e) {
            logger.error("Error taking screenshot: {}", e.getMessage());
        }
    }

    @Given("I connect to the database")
    public void connectToDatabase() throws SQLException {
        logger.info("Connecting to database");
        connection = TestContext.getConnection();
    }

    @Then("I verify the user with email {string} exists in the database")
    public void verifyUserInDatabase(String email) throws SQLException {
        logger.info("Verifying user with email {} in database", email);
        String query = "SELECT * FROM users WHERE email = '" + email + "'";
        try (Statement stmt = connection.createStatement();
             ResultSet rs = stmt.executeQuery(query)) {
            if (rs.next()) {
                logger.info("User found: {}", rs.getString("email"));
            } else {
                logger.error("User with email {} not found", email);
                throw new AssertionError("User with email " + email + " not found in database");
            }
        }
    }

    @Then("I close the database connection")
    public void closeDatabaseConnection() {
        logger.info("Closing database connection");
        TestContext.closeConnection();
    }
}