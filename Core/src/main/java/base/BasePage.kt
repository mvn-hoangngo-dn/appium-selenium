package base

import core.AppiumController
import io.appium.java_client.MobileDriver
import io.appium.java_client.MobileElement
import io.appium.java_client.PerformsTouchActions
import io.appium.java_client.TouchAction
import io.appium.java_client.pagefactory.AppiumFieldDecorator
import io.appium.java_client.touch.WaitOptions
import io.appium.java_client.touch.offset.PointOption
import org.openqa.selenium.By
import org.openqa.selenium.Dimension
import org.openqa.selenium.NoSuchElementException
import org.openqa.selenium.WebDriver
import org.openqa.selenium.WebElement
import org.openqa.selenium.interactions.Actions
import org.openqa.selenium.remote.RemoteWebDriver
import org.openqa.selenium.support.PageFactory
import org.openqa.selenium.support.ui.ExpectedConditions
import org.openqa.selenium.support.ui.WebDriverWait
import java.io.BufferedReader
import java.io.InputStreamReader
import java.time.Duration

/**
 * @author tien.hoang
 */
abstract class BasePage<T> {

    /**
     * Wait for element displayed with timeout
     *
     * @param by
     * @param timeOutInSecond
     */
    fun waitForElementDisplay(by: By?, timeOutInSecond: Int) {
        try {
            WebDriverWait(
                getDriver(), timeOutInSecond.toLong()
            ).until { driver: WebDriver? -> isElementDisplayed(driver!!.findElement(by) as MobileElement) }
        } catch (e: Exception) {
            // no-opt
        }
    }



    fun removeApp() {
        getDriver()?.removeApp(Const.bundleId)
    }

    /**
     * Wait for element displayed with timeout
     *
     * @param element
     * @param timeOutInSecond
     */
    fun waitForElementDisplay(element: MobileElement?, timeOutInSecond: Int) {
        try {
            WebDriverWait(getDriver(), timeOutInSecond.toLong()).until { isElementDisplayed(element) }
        } catch (e: Exception) {
            // no-opt
        }
    }

    fun waitForWebElementDisplay(element: WebElement) {
        try {
            WebDriverWait(getWebDriver(), Const.TIME_OUT_NORMAL_ELEMENT.toLong()).until { isWebElementDisplayed(element) }
        } catch (e: Exception) {
            // no-opt
        }
    }

    /**
     * Get Mobile driver
     *
     * @return mobile driver
     */
    open fun getDriver(): MobileDriver<*>? {
        return AppiumController.instance.getDriver()
    }

    /**
     * Get Web driver
     *
     * @return web driver
     */
    open fun getWebDriver(): RemoteWebDriver? {
        return AppiumController.instance.getWebDriver()
    }

    fun clickNotification(element: MobileElement?): BasePage<*> {
        try {
            WebDriverWait(getDriver(), Const.TIME_OUT_NORMAL_ELEMENT.toLong()).until { isElementDisplayed(element) }
            element?.click()
        } catch (e: Exception) {
        }
        return this
    }

    /**
     * Wait for element displayed without timeout
     *
     * @param element
     */
    fun waitForElementDisplay(element: MobileElement?) {
        try {
            WebDriverWait(getDriver(), Const.TIME_OUT_NORMAL_ELEMENT.toLong()).until { isElementDisplayed(element) }
        } catch (e: Exception) {
            // no-opt
        }
    }

    fun launchApp() {
        getDriver()?.launchApp()
    }

    /**
     * Wait for element displayed without timeout
     *
     * @param element
     */
    fun waitForElementEnable(element: MobileElement) {
        try {
            WebDriverWait(getDriver(), Const.TIME_OUT_NORMAL_ELEMENT.toLong()).until { element.isEnabled }
        } catch (e: Exception) {
            // no-opt
        }
    }

    @Synchronized
    fun waitForNextStep(time: Int) {
        val start = System.currentTimeMillis()
        val end = start + time * 1000
        while (System.currentTimeMillis() < end) {
            //Not to do anything
        }
    }

    /**
     * Wait for element hide without timeout
     *
     * @param element
     */
    fun waitForElementHide(element: MobileElement?) {
        try {
            WebDriverWait(getDriver(), Const.TIME_OUT_NORMAL_ELEMENT.toLong()).until { !isElementDisplayed(element)!! }
        } catch (e: Exception) {
            // no-opt
        }
    }

    fun clickNotificationTimeline(element: MobileElement?, time: Int) {
        try {
            waitForNextStep(time)
            element?.click()
        } catch (e: Exception) {
        }
    }

    /**
     * Wait for page displayed
     *
     * @param element
     */
    fun waitForPageDisplayed(element: MobileElement?): BasePage<*> {
        try {
            WebDriverWait(getDriver(), Const.TIME_OUT_NORMAL_ELEMENT.toLong()).until { isElementDisplayed(element) }
        } catch (e: Exception) {
            // no-opt
        }
        return this
    }

    /**
     * Check for screen is displayed
     *
     * @param element
     * @return
     */
    fun isForElementPresent(element: MobileElement?): Boolean {
        return isElementDisplayed(element, Const.TIME_OUT_MIN_ELEMENT)
    }

    /**
     * Wait for list of element displayed
     *
     * @param elements
     */
    fun waitForListElementDisplay(elements: List<MobileElement>?) {
        try {
            WebDriverWait(
                getDriver(),
                Const.TIME_OUT_NORMAL_ELEMENT.toLong()
            ).until { isListElementDisplayed(elements) }
        } catch (e: Exception) {
        }
    }

    /**
     * Check if list of element displayed
     *
     * @param elements
     */
    private fun isListElementDisplayed(elements: List<MobileElement>?): Boolean? {
        return try {
            elements?.isNotEmpty()
        } catch (exception: Exception) {
            exception.printStackTrace()
            false
        }
    }

    fun scrollDownSmall(): BasePage<*> {
        try {
            getDriver()?.let {
                val dimensions = it.manage().window().size
                val startPosition = (dimensions.getHeight() * 0.8).toInt()
                val endPosition = (dimensions.getHeight() * 0.7).toInt()
                PlatformTouchAction(it).press(PointOption.point(OFFSET_X, startPosition))
                    .waitAction(WaitOptions.waitOptions(Duration.ofMillis(DURATION.toLong())))
                    .moveTo(PointOption.point(OFFSET_X, endPosition)).release().perform()
            }
            Thread.sleep(2000)
        } catch (e: Exception) {
        }
        return this
    }

    fun scrollUp(): BasePage<*> {
        try {
            getDriver()?.let {
                val dimensions = it.manage().window().size
                val startPosition = (dimensions.getHeight() * OFFSET2).toInt()
                val endPosition = (dimensions.getHeight() * OFFSET1).toInt()
                PlatformTouchAction(it).press(PointOption.point(OFFSET_X, startPosition))
                    .waitAction(WaitOptions.waitOptions(Duration.ofMillis(DURATION.toLong())))
                    .moveTo(PointOption.point(OFFSET_X, endPosition)).release().perform()
            }
        } catch (e: Exception) {
        }
        return this
    }

    fun scrollUpSmall(): BasePage<*> {
        try {
            getDriver()?.let {
                val dimensions = it.manage().window().size
                val startPosition = (dimensions.getHeight() * 0.5).toInt()
                val endPosition = (dimensions.getHeight() * 0.8).toInt()
                PlatformTouchAction(it).press(PointOption.point(OFFSET_X, startPosition))
                    .waitAction(WaitOptions.waitOptions(Duration.ofMillis(DURATION.toLong())))
                    .moveTo(PointOption.point(OFFSET_X, endPosition)).release().perform()
                Thread.sleep(2000)
            }
        } catch (e: Exception) {
            e.printStackTrace()
        }
        return this
    }

    fun scrollWithSpecialOffset(startOffset: Double, endOffset: Double): BasePage<*> {
        try {
            getDriver()?.let {
                val dimensions = it.manage().window().size
                val startPosition = (dimensions.getHeight() * startOffset).toInt()
                val endPosition = (dimensions.getHeight() * endOffset).toInt()
                PlatformTouchAction((it)).press(PointOption.point(OFFSET_X, startPosition))
                    .waitAction(WaitOptions.waitOptions(Duration.ofMillis(DURATION.toLong())))
                    .moveTo(PointOption.point(OFFSET_X, endPosition)).release().perform()
                Thread.sleep(2000)
            }
        } catch (e: Exception) {
            e.printStackTrace()
        }
        return this
    }

    /**
     * Check page is displayed
     *
     * @return boolean
     */
    abstract fun isPageDisplayed(): Boolean?

    /**
     * Click back button
     */
    open fun clickBackButton(): BasePage<*> {
        return this
    }

    /**
     * Navigate to page
     */
    abstract fun open(): T

    fun scrollToElement(element: MobileElement) {
        var count = 0
        while (!isForElementPresent(element) && count < 5) {
            scrollToView()
            count++
        }
    }

    /**
     * Scroll to see elements
     */
    private fun scrollToView(): BasePage<*> {
        try {
            getDriver()?.let {
                val dimensions = it.manage().window().size
                val startPosition = (dimensions.getHeight() * OFFSET1).toInt()
                val endPosition = (dimensions.getHeight() * OFFSET2).toInt()
                PlatformTouchAction(it).press(PointOption.point(OFFSET_X, startPosition))
                    .waitAction(WaitOptions.waitOptions(Duration.ofMillis(DURATION.toLong())))
                    .moveTo(PointOption.point(OFFSET_X, endPosition)).release().perform()
                Thread.sleep(2000)
            }
        } catch (e: Exception) {
        }
        return this
    }

    fun scrollToAccount(start: Float, end: Float, platform: String): BasePage<*> {
        try {
            getDriver()?.let {
                val dimensions = it.manage().window().size
                val startPosition = if (platform == "ios") {
                    (dimensions.getHeight() * start).toInt()
                } else {
                    (dimensions.getHeight() / start).toInt()
                }
                val endPosition = (dimensions.getHeight() * end).toInt()
                PlatformTouchAction(it)
                    .press(PointOption.point(dimensions.getWidth() / 2, startPosition))
                    .waitAction(WaitOptions.waitOptions(Duration.ofMillis(DURATION.toLong())))
                    .moveTo(PointOption.point(dimensions.getWidth() / 2, endPosition))
                    .release()
                    .perform()
                Thread.sleep(2000)
            }
        } catch (e: Exception) {
        }
        return this
    }

    /**
     * Click notification alerts
     */
    fun clickNotification(by: By?): BasePage<*> {
        try {
            WebDriverWait(
                getDriver(), Const.TIME_OUT_MEDIUM_ELEMENT.toLong()
            ).until { driver: WebDriver? -> isElementDisplayed(driver!!.findElement(by) as MobileElement) }
            getDriver()?.findElement(by)?.click()
        } catch (e: Exception) {
            // no-opt
        }
        return this
    }

    fun tapOnScreen(percent: Double): BasePage<*> {
        getDriver()?.let {
            val dimensions = it.manage().window().size
            val y = (dimensions.getHeight() * percent).toInt()
            val x = (dimensions.getWidth() * 0.5).toInt()
            PlatformTouchAction(it).tap(PointOption.point(x, y)).perform()
        }
        return this
    }

    fun tapOnScreen(percent: Double, dimensions: Dimension): BasePage<*> {
        val y = (dimensions.getHeight() * percent).toInt()
        val x = (dimensions.getWidth() * 0.5).toInt()
        PlatformTouchAction(getDriver()!!).tap(PointOption.point(x, y)).perform()
        return this
    }

    fun doubleClick(mobileElement: MobileElement) {
        getDriver()?.let {
            var xPoint = 1
            var yPoint = 1
            if (isForElementPresent(mobileElement)) {
                xPoint = mobileElement.location.getX()
                yPoint = mobileElement.location.getY()
            }
            try {
                PlatformTouchAction(it).press(PointOption.point(xPoint, yPoint)).release().perform()
                    .press(PointOption.point(xPoint, yPoint)).release().perform()
            } catch (e: Exception) {
                e.printStackTrace()
                println("Fail to doubleClick")
            }
        }
    }

    fun clickSystemNotification(element: MobileElement?): BasePage<*> {
        val action = Actions(getDriver())
        action.moveToElement(element)
        action.click()
        action.perform()
        return this
    }

    /**
     * ResetApp to logout app for android devices
     */
    fun resetApplication(): BasePage<*> {
        getDriver()?.resetApp()
        return this
    }

    fun swipeRight(): BasePage<*> {
        getDriver()?.let {
            val size = it.manage().window().size
            val startY = size.height / 2
            val startX = (size.width * OFFSET_RIGHT1).toInt()
            val endX = (size.width * OFFSET_RIGHT2).toInt()
            PlatformTouchAction(it).press(PointOption.point(startX, startY))
                .waitAction(WaitOptions.waitOptions(Duration.ofMillis(DURATION.toLong())))
                .moveTo(PointOption.point(endX, startY)).release().perform()
        }
        return this
    }

    fun swipeLeft(): BasePage<*> {
        getDriver()?.let {
            val size = it.manage().window().size
            val startY = size.height / 2
            val endX = (size.width * OFFSET_LEFT1).toInt()
            val startX = (size.width * OFFSET_LEFT2).toInt()
            PlatformTouchAction(it).press(PointOption.point(endX, startY))
                .waitAction(WaitOptions.waitOptions(Duration.ofMillis(DURATION.toLong())))
                .moveTo(PointOption.point(startX, startY)).release().perform()
        }
        return this
    }

    /**
     * Check get gmail success
     */
    fun waitToCallGmailApiSuccess(apiStatus: Boolean) {
        val wait = WebDriverWait(getDriver(), Const.TIME_OUT_NORMAL_ELEMENT.toLong())
        try {
            wait.until { apiStatus }
        } catch (e: Exception) {
            // No-opt
        }
    }

    /**
     * Check element displayed for pages
     */
    private fun isElementDisplayed(element: MobileElement?, timeOutInSecond: Int): Boolean {
        var isVisible = false
        val wait = WebDriverWait(getDriver(), timeOutInSecond.toLong())
        try {
            if (wait.until(ExpectedConditions.visibilityOf(element)) != null) {
                isVisible = true
            }
        } catch (e: Exception) {
            // No-opt
        }
        return isVisible
    }

    protected fun isElementDisplayed(element: MobileElement?): Boolean? {
        return try {
            element?.isDisplayed
        } catch (e: NoSuchElementException) {
            false
        }
    }

    protected fun isWebElementDisplayed(element: WebElement?): Boolean? {
        return try {
            element?.isDisplayed
        } catch (e: NoSuchElementException) {
            false
        }
    }

    fun manageNotifications(show: Boolean) {
        getDriver()?.let {
            val screenSize = it.manage().window().size

            val yMargin = 5
            val xMid: Int = screenSize.width / 2
            val top = PointOption.point(xMid, yMargin)
            val bottom = PointOption.point(xMid, screenSize.height - yMargin)
            val action = PlatformTouchAction(it)
            if (show) {
                action.press(top)
            } else {
                action.press(bottom)
            }
            action.waitAction(WaitOptions.waitOptions(Duration.ofSeconds(1)))
            if (show) {
                action.moveTo(bottom)
            } else {
                action.moveTo(top)
            }
            action.perform()
        }
    }

    fun openDeepLink(deeplink: String) {
        Thread.sleep(3000)
        getDriver()?.get(deeplink)
    }

    companion object {
        private const val OFFSET1 = 0.8
        private const val OFFSET2 = 0.2
        private const val DURATION = 500
        private const val OFFSET_X = 10
        private const val OFFSET_RIGHT1 = 0.90
        private const val OFFSET_RIGHT2 = 0.05
        private const val OFFSET_LEFT1 = 0.30
        private const val OFFSET_LEFT2 = 0.70
    }

    init {
        PageFactory.initElements(AppiumFieldDecorator(getDriver()), this)
    }
}

class PlatformTouchAction(performsTouchActions: PerformsTouchActions) :
    TouchAction<PlatformTouchAction>(performsTouchActions)
