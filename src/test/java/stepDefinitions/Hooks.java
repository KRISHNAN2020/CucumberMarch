package stepDefinitions;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.reporter.ExtentSparkReporter;
import io.cucumber.java.After;
import io.cucumber.java.Before;
import io.cucumber.java.Scenario;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;

public class Hooks {
    private static final Logger logger = LogManager.getLogger(Hooks.class);
    private static ExtentReports extent;
    private static ExtentTest test;

    @Before
    public void setup(Scenario scenario) {
        logger.info("Starting scenario: {}", scenario.getName());
        if (extent == null) {
            ExtentSparkReporter spark = new ExtentSparkReporter("target/ExtentReports.html");
            spark.config().setReportName("Cucumber Test Report");
            spark.config().setDocumentTitle("Test Execution Report");
            extent = new ExtentReports();
            extent.attachReporter(spark);
        }
        test = extent.createTest(scenario.getName());
        test.info("Starting scenario: " + scenario.getName());
    }

    @After
    public void teardown(Scenario scenario) {
        if (scenario.isFailed()) {
            logger.error("Scenario failed: {}", scenario.getName());
            test.fail("Scenario Failed: " + scenario.getName());
            try {
                String screenshot = ((TakesScreenshot) TestContext.getDriver()).getScreenshotAs(OutputType.BASE64);
                scenario.attach(((TakesScreenshot) TestContext.getDriver()).getScreenshotAs(OutputType.BYTES), "image/png", "Failure Screenshot");
                test.addScreenCaptureFromBase64String(screenshot, "Failure Screenshot");
                logger.info("Screenshot attached to report for failed scenario");
                test.info("Screenshot attached for failed scenario");
            } catch (Exception e) {
                logger.error("Failed to take screenshot: {}", e.getMessage());
                test.fail("Failed to take screenshot: " + e.getMessage());
            }
        } else {
            logger.info("Scenario passed: {}", scenario.getName());
            test.pass("Scenario Passed: " + scenario.getName());
        }
        extent.flush();
        TestContext.closeDriver();
        TestContext.closeConnection();
    }

    // Method to log steps in Extent Reports from step definitions
    public static void logStep(String stepDetails) {
        if (test != null) {
            test.info(stepDetails);
        }
    }
}