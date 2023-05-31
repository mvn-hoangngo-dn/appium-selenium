package core

import org.testng.TestListenerAdapter
import org.testng.ITestContext
import java.io.FileOutputStream
import java.io.BufferedWriter
import java.io.File
import java.io.OutputStreamWriter
import java.lang.Exception

/**
 * @author tien.hoang
 */
class PropertyListener : TestListenerAdapter() {
    override fun onStart(iTestContext: ITestContext) {
        val fileName = iTestContext.name.replace(" ", "_") + ".properties"
        val reportDirectory = File(System.getProperty("user.dir")).absolutePath + "/target/classifications"
        val directory = File(reportDirectory)
        if (!directory.exists()) {
            directory.mkdirs()
        }
        val destFile = File("$reportDirectory/$fileName")
        try {
            val fos = FileOutputStream(destFile)
            val bw = BufferedWriter(OutputStreamWriter(fos))
            for ((key, value) in iTestContext.currentXmlTest.allParameters) {
                bw.write("$key:$value")
                bw.newLine()
            }
            bw.close()
        } catch (e: Exception) {
            e.printStackTrace()
        }
        super.onStart(iTestContext)
    }
}