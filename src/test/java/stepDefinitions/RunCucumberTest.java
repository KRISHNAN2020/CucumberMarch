package stepDefinitions;

import io.cucumber.testng.AbstractTestNGCucumberTests;
import io.cucumber.testng.CucumberOptions;

@CucumberOptions(
        features = "src/test/resources/features",
        glue = {"stepDefinitions"},  // Corrected to point to the package
        plugin = { "pretty", "html:target/cucumber-reports/cucumber.html", "json:target/cucumber-reports/cucumber.json", "tech.grasshopper.extentreports.cucumber.adapter.ExtentCucumberAdapter:" },
        monochrome = true
)
public class RunCucumberTest extends AbstractTestNGCucumberTests {
}