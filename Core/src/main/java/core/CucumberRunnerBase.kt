package core

import org.testng.annotations.Listeners
import io.cucumber.testng.AbstractTestNGCucumberTests
import org.testng.annotations.DataProvider
import kotlin.Throws
import org.testng.ITestContext
import org.testng.annotations.AfterClass
import org.testng.annotations.BeforeClass
import java.lang.Exception

/**
 * @author tien.hoang
 */
@Listeners(ScreenshotListener::class, PropertyListener::class, ReportListener::class)
abstract class CucumberRunnerBase : AbstractTestNGCucumberTests() {
    @DataProvider
    fun features(): Array<Array<Any>> {
        return super.scenarios()
    }

    @BeforeClass(alwaysRun = true)
    @Throws(Exception::class)
    fun setUpClass(context: ITestContext) {
        super.setUpClass()
        AppiumController.instance.start(context.currentXmlTest)
    }

    @AfterClass(alwaysRun = true)
    @Throws(Exception::class)
    override fun tearDownClass() {
        super.tearDownClass()
        println("tearDownClass")
        AppiumController.instance.quitWebDriver()
        AppiumController.instance.stop()
    }
}