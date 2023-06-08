package stepdefs.testRunner

import core.CucumberRunnerBase
import io.cucumber.testng.CucumberOptions

/**
 * @author at-anhquach
 * CucumberOptions
 */
@CucumberOptions(
    features = ["src/test/resources/features"],
    glue = ["stepdefs"],
    tags = "@web",
    plugin = ["pretty", "junit:target/cucumber-reports/junit-report.xml", "html:target/cucumber-reports/cucumber-pretty", "json:target/cucumber-reports/CucumberTestReport.json", "rerun:target/cucumber-reports/rerun.txt"]
)
internal class ChromeTestRunner : CucumberRunnerBase()
