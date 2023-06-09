package core

import org.testng.ITestContext
import org.testng.annotations.AfterClass
import org.testng.annotations.BeforeClass
import org.testng.annotations.DataProvider
import org.testng.annotations.Listeners

/**
 * @author hang.tran
 */
@Listeners(ScreenshotListener::class, PropertyListener::class, ReportListener::class)
open class ParallelCucumberRunnerBase : CustomAbstractTestNGCucumberTests() {
    @DataProvider
    fun features(): Array<*> {
        return super.scenarios()
    }

    @BeforeClass(alwaysRun = true)
    @Throws(Exception::class)
    override fun setUpClass(context: ITestContext) {
        super.setUpClass(context)
        println("Set Up Class.")
        AppiumController.instance.start(context.currentXmlTest)
    }

    @AfterClass(alwaysRun = true)
    @Throws(Exception::class)
    override fun tearDownClass() {
        super.tearDownClass()
        println("Tear Down Class.")
        AppiumController.instance.stop()
    }
}