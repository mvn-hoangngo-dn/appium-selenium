package pages.webPages


import base.BasePage
import org.openqa.selenium.By
import org.openqa.selenium.WebElement

import org.openqa.selenium.support.FindBy


class HomePortalPage : BasePage<HomePortalPage>() {

    @FindBy(className = "welcome-message")
    private lateinit var txtWelcomeTitle: WebElement

    @FindBy(className = "m-t-n")
    private lateinit var txtNewsFeed: WebElement


    override fun open(): HomePortalPage {
        return this
    }

    override fun isPageDisplayed(): Boolean? {
        var txtWelcome = getWebDriver()?.findElement(By.className("welcome-messag"))
        return isWebElementDisplayed(txtWelcome)
    }

    fun waitForPageDisplayed(): HomePortalPage {
        waitForWebElementDisplay(txtNewsFeed)
        return this
    }
}
