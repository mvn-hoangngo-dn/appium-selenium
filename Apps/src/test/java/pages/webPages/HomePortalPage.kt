package pages.webPages


import base.BasePage
import ml.utils.Constant
import org.openqa.selenium.By
import org.openqa.selenium.WebElement
import org.openqa.selenium.remote.RemoteWebDriver

import org.openqa.selenium.support.FindBy


class HomePortalPage(private val driver: RemoteWebDriver) : BasePage<HomePortalPage>() {

    @FindBy(className = "welcome-message")
    private lateinit var txtWelcomeTitle: WebElement

    @FindBy(className = "m-t-n")
    private lateinit var txtNewsFeed: WebElement


    override fun open(): HomePortalPage {
        getWebDriver()?.get(Constant.HOME_PAGE_URL)
        waitForWebElementDisplay(txtNewsFeed)
        return this
    }

    override fun isPageDisplayed(): Boolean? {
        var txtWelcome = driver.findElement(By.className("welcome-message"))
        return isWebElementDisplayed(txtWelcome)
    }

    fun waitForPageDisplayed(): HomePortalPage {
        waitForWebElementDisplay(txtNewsFeed)
        return this
    }
}
