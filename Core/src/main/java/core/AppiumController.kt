package core

import base.Const
import io.appium.java_client.AppiumDriver
import io.appium.java_client.MobileElement
import io.appium.java_client.android.AndroidDriver
import io.appium.java_client.ios.IOSDriver
import io.appium.java_client.remote.AndroidMobileCapabilityType
import io.appium.java_client.remote.IOSMobileCapabilityType
import io.appium.java_client.remote.MobileCapabilityType
import org.openqa.selenium.Dimension
import org.openqa.selenium.Point
import org.openqa.selenium.Proxy
import org.openqa.selenium.WebElement
import org.openqa.selenium.remote.CapabilityType
import org.openqa.selenium.remote.DesiredCapabilities
import org.openqa.selenium.remote.RemoteWebDriver
import org.testng.ITestContext
import org.testng.xml.XmlTest
import java.net.MalformedURLException
import java.net.URL
import java.util.*
import java.util.concurrent.TimeUnit


/**
 * @author tien.hoang
 */
internal open class AppiumController {
    private val driverFactoryThread = ThreadLocal<AppiumDriver<*>?>()
    private val webDriverFactoryThread = ThreadLocal<RemoteWebDriver>()
    private lateinit var driver: AppiumDriver<*>
    private lateinit var webDriver: RemoteWebDriver
    private var webContext: ITestContext? = null
    private var context: ITestContext? = null
    private val proxyEnabled = java.lang.Boolean.getBoolean("proxyEnabled")
    private val proxyHostname = System.getProperty("proxyHost")
    private val proxyPort = Integer.getInteger("proxyPort")
    private val proxyDetails = String.format("%s:%d", proxyHostname, proxyPort)

    /**
     * Get Appium driver #synchronized
     *
     * @return appium driver
     */
    @Synchronized
    fun getDriver(): AppiumDriver<*>? {
        if (driverFactoryThread.get() == null) {
            try {
                startDefaultServer()
            } catch (e: MalformedURLException) {
                e.printStackTrace()
            }
        }
        return driverFactoryThread.get()
    }

    /**
     * Get Web driver #synchronized
     *
     * @return web driver
     */
    @Synchronized
    fun getWebDriver(): RemoteWebDriver? {
        if (webDriverFactoryThread.get() == null) {
            if (this.webContext == null) {
                val xmlTest = XmlTest()
                xmlTest.setParameters(defaultWebParameters())
                start(xmlTest)
            }
        }
        return webDriverFactoryThread.get()
    }

    /**
     * Start test session
     *
     * @param xmlTest
     * @throws MalformedURLException
     */
    @Synchronized
    @Throws(MalformedURLException::class)
    fun start(xmlTest: XmlTest) {
        val capabilities = parseCapabilities(xmlTest)
        val platformName = capabilities.getCapability(MobileCapabilityType.PLATFORM_NAME)
        if (platformName != null) {
            capabilities.setCapability(MobileCapabilityType.NO_RESET, true)
            capabilities.setCapability(MobileCapabilityType.NEW_COMMAND_TIMEOUT, 1200)
            when {
                platformName.toString().equals("android", ignoreCase = true) -> {
                    // Android platform
                    driver = AndroidDriver<MobileElement>(URL(xmlTest.getParameter("server")), capabilities)
                    (driver as AndroidDriver<*>?)!!.unlockDevice()
                }
                platformName.toString().equals("ios", ignoreCase = true) -> {
//                capabilities.setCapability("useNewWDA", true)
//                capabilities.setCapability("usePrebuiltWDA", true)
                    // iOS platform
                    driver = IOSDriver<WebElement>(URL(xmlTest.getParameter("server")), capabilities)
                }
                else -> {
                    println(
                        "Error: Unknown platform " + capabilities.getCapability(MobileCapabilityType.PLATFORM_NAME)
                            .toString()
                    )
                }
            }
            driver.manage().timeouts().implicitlyWait(Const.TIME_OUT_MIN_ELEMENT.toLong(), TimeUnit.SECONDS)
            val contextNames = driver.contextHandles
            for (contextName in contextNames) {
                if (contextName.contains("WEBVIEW") || contextName.contains("NATIVE_APP")) {
                    driver.context(contextName)
                    break
                }
            }
            driverFactoryThread.set(driver)
            context?.setAttribute("driver", webDriver)
        } else {
            val server = xmlTest.getParameter("server")
            val headLess = xmlTest.getParameter("headless")
            val driverType = DriverType.CHROME
            try {
                instantiateWebDriver(driverType, server, java.lang.Boolean.parseBoolean(headLess))
            } catch (e: MalformedURLException) {
                e.printStackTrace()
            }
            webDriver.manage()?.timeouts()?.implicitlyWait(Const.TIME_OUT_MIN_ELEMENT.toLong(), TimeUnit.SECONDS)
            webDriverFactoryThread.set(webDriver)
            webContext?.setAttribute("driver", webDriver)
        }
    }

    /**
     * Stop test session
     */
    fun stop() {
        driver.quit()
    }

    @Throws(MalformedURLException::class)
    private fun instantiateWebDriver(driverType: DriverType, server: String?, isHeadLess: Boolean) {
        val desiredCapabilities = DesiredCapabilities()
        if (proxyEnabled) {
            val proxy = Proxy()
            proxy.proxyType = Proxy.ProxyType.MANUAL
            proxy.httpProxy = proxyDetails
            proxy.sslProxy = proxyDetails
            desiredCapabilities.setCapability(CapabilityType.PROXY, proxy)
        }
        if (server != null && server.isNotEmpty()) {
            val seleniumGridURL = URL(server)
            desiredCapabilities.browserName = driverType.toString()
            webDriver = RemoteWebDriver(seleniumGridURL, desiredCapabilities)
        } else {
            webDriver = driverType.getWebDriverObject(desiredCapabilities, isHeadLess)
        }
        webDriver.manage().window().position = Point(490, 47)
        webDriver.manage()?.window()?.size =
            Dimension(webDriver.manage()?.window()?.size?.height!! + 200, webDriver.manage()?.window()?.size?.height!!)
        driverFactoryThread.set(driver)
    }

    @Synchronized
    @Throws(MalformedURLException::class)
    private fun startDefaultServer() {
        val xmlTest = XmlTest()
        xmlTest.setParameters(defaultIosParameters())
        start(xmlTest)
    }

    private fun parseCapabilities(xmlTest: XmlTest): DesiredCapabilities {
        var workSpace: String
        if (System.getProperty("workSpace") != null) {
            workSpace = System.getProperty("workSpace")
        } else {
            workSpace = System.getProperty("user.dir")
            if (workSpace.contains("/Apps")) {
                workSpace = workSpace.replace("/Apps", "")
            }
        }
        val capabilities = DesiredCapabilities()
        try {
            println("xmlTest xmlTest xmlTest ${xmlTest.localParameters}")
        } catch (e: Exception) {
        }
        for (key in xmlTest.localParameters.keys) {
            var value = xmlTest.localParameters[key]
            if (key.equals(MobileCapabilityType.APP, ignoreCase = true)) {
                if (!value!!.startsWith("http")) {
                    value = workSpace + value
                }
            }
            if (!key.equals("server", ignoreCase = true)) {
                println("DesiredCapabilities: $key: $value")
                if (value == "false" || value == "true") {
                    capabilities.setCapability(key, value.toBoolean())
                } else {
                    capabilities.setCapability(key, value)
                }
            }
        }
        return capabilities
    }

    private fun defaultAndroidParameters(): Map<String, String> {
        val parameters: MutableMap<String, String> = HashMap()
        parameters[MobileCapabilityType.PLATFORM_NAME] = "android"
        parameters[MobileCapabilityType.DEVICE_NAME] = "SamSung Galaxy"
        parameters[MobileCapabilityType.PLATFORM_VERSION] = "11"
        parameters.put(MobileCapabilityType.UDID, "R58N22PKRQV")
        parameters[MobileCapabilityType.AUTOMATION_NAME] = "UiAutomator2"
        parameters[AndroidMobileCapabilityType.APP_PACKAGE] = "com.wdiodemoapp"
        parameters[AndroidMobileCapabilityType.APP_ACTIVITY] = "com.wdiodemoapp.MainActivity"
        parameters[MobileCapabilityType.APP] = "/appfile/android/Android-NativeDemoApp-0.4.0.apk"
        parameters["server"] = "http://127.0.0.1:4723/wd/hub"
        return parameters
    }

    private fun defaultIosParameters(): Map<String, String> {
        val parameters: MutableMap<String, String> = HashMap()
        parameters[MobileCapabilityType.PLATFORM_NAME] = "ios"
        parameters[MobileCapabilityType.DEVICE_NAME] = "iPhone 12"
        parameters[IOSMobileCapabilityType.XCODE_SIGNING_ID] = "iPhone Developer"
        parameters[MobileCapabilityType.PLATFORM_VERSION] = "14.5"
//        parameters[MobileCapabilityType.UDID] = "00008101-000600E62EB9003A"
//        parameters[IOSMobileCapabilityType.UPDATE_WDA_BUNDLEID] = "vn.ml.webdriveragent"
        parameters[MobileCapabilityType.AUTOMATION_NAME] = "XCUITest"
        parameters[MobileCapabilityType.APP] = "/appfile/ios/wdioNativeDemoApp.app"
        parameters["server"] = "http://127.0.0.1:4723/wd/hub"
        return parameters
    }

    private fun defaultWebParameters(): Map<String, String>? {
        val parameters: MutableMap<String, String> = HashMap()
        parameters[MobileCapabilityType.BROWSER_NAME] = "chrome"
        parameters["headless"] = "false"
        parameters["server"] = "http://118.69.61.190:4445"
//        parameters["server"] = ""
        if (webContext == null) {
            return parameters
        } else {
            quitWebDriver()
        }
        return null
    }

    fun quitWebDriver() {
        webDriverFactoryThread.get()?.quit()
        webDriverFactoryThread.remove()
        webDriver.quit()
    }

    companion object {
        @JvmField
        var instance = AppiumController()
    }
}
