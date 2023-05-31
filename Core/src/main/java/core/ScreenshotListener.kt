package core

import org.apache.commons.io.FileUtils
import org.openqa.selenium.OutputType
import org.openqa.selenium.TakesScreenshot
import org.testng.ITestResult
import org.testng.TestListenerAdapter
import java.io.File
import java.io.IOException
import java.text.SimpleDateFormat
import java.util.*

/**
 * @author tien.hoang
 */
class ScreenshotListener : TestListenerAdapter() {
    override fun onTestFailure(result: ITestResult) {
        val calendar = Calendar.getInstance()
        val format = SimpleDateFormat("dd_MM_yyyy_hh_mm_ss")
        val methodName = result.name
        if (!result.isSuccess) {
            val scrFile = (AppiumController.instance.getDriver() as TakesScreenshot).getScreenshotAs(OutputType.FILE)
            try {
                val reportDirectory = File(System.getProperty("user.dir")).absolutePath + "/target"
                val destFile =
                    File(reportDirectory + "/screenshots/" + methodName + "_" + format.format(calendar.time) + ".png")
                FileUtils.copyFile(scrFile, destFile)
            } catch (e: IOException) {
                e.printStackTrace()
            }
        }
    }
}