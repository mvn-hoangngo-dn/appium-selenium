package base

import core.AppiumBaseClass
import core.AppiumController
import core.PageFactory
import io.cucumber.java8.En
import io.cucumber.java8.Scenario
import org.openqa.selenium.OutputType
import org.openqa.selenium.TakesScreenshot
import org.openqa.selenium.remote.RemoteWebDriver
import org.openqa.selenium.support.PageFactory.initElements
import org.openqa.selenium.support.pagefactory.DefaultElementLocatorFactory

/**
 * @author tien.hoang
 */
abstract class BaseDefinitions : AppiumBaseClass(), En {


    fun getWebDriver(): RemoteWebDriver? {
        return AppiumController.instance.getWebDriver()
    }

    fun quitWebDriver() {
        AppiumController.instance.quitWebDriver()
    }

    protected fun embedScreenshot(scenario: Scenario) {
        val ts = AppiumController.instance.getDriver() as TakesScreenshot
        val src = ts.getScreenshotAs(OutputType.BYTES)
        scenario.attach(src, "image/png", "Screenshot: " + scenario.name)
    }
}
