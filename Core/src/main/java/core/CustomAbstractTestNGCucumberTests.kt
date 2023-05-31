package core

import io.cucumber.testng.FeatureWrapper
import io.cucumber.testng.PickleWrapper
import io.cucumber.testng.TestNGCucumberRunner
import org.testng.ITestContext
import org.testng.annotations.AfterClass
import org.testng.annotations.BeforeClass
import org.testng.annotations.DataProvider
import org.testng.annotations.Test

/**
 * @author hang.tran
 */
abstract class CustomAbstractTestNGCucumberTests {
    private var testNGCucumberRunner: TestNGCucumberRunner? = null
    private var threadCount = 0

    @BeforeClass(alwaysRun = true)
    @Throws(Exception::class)
    open fun setUpClass(context: ITestContext) {
        threadCount = context.currentXmlTest.suite.tests[0].threadCount
        testNGCucumberRunner = TestNGCucumberRunner(this.javaClass)
    }

    @Test(groups = ["cucumber"], description = "Runs Cucumber Scenarios", dataProvider = "scenarios")
    @Throws(Throwable::class)
    open fun scenario(pickle: PickleWrapper, cucumberFeature: FeatureWrapper?) {
        testNGCucumberRunner?.runScenario(pickle.pickle)
    }

    @DataProvider
    fun scenarios(): Array<*> {
        val scenarios = ArrayList(listOf(testNGCucumberRunner!!.provideScenarios()))
        val scenarioPerThread = scenarios.size / threadCount
        val runScenarios: List<*> = if (browserCount == threadCount - 1) scenarios.subList(
            browserCount * scenarioPerThread,
            scenarios.size
        ) else scenarios.subList(
            browserCount * scenarioPerThread, browserCount * scenarioPerThread + scenarioPerThread
        )
        println("Thread " + browserCount + " run " + runScenarios.size + " scenarios")
        browserCount++
        return if (testNGCucumberRunner == null) Array(0) { 0 } else runScenarios.toTypedArray()

    }

    @AfterClass(alwaysRun = true)
    @Throws(Exception::class)
    open fun tearDownClass() {
        if (testNGCucumberRunner != null) {
            testNGCucumberRunner?.finish()
        }
    }

    companion object {
        private var browserCount = 0
    }
}

