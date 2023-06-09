package pages.appPages

import base.BasePage
import io.appium.java_client.MobileElement
import io.appium.java_client.pagefactory.AndroidFindBy
import io.appium.java_client.pagefactory.iOSFindBy

class HomePage: BasePage<HomePage>() {

    @AndroidFindBy(xpath = "//*[@text='WEBDRIVER']")
    @iOSFindBy(id = "WEBDRIVER")
    private lateinit var txtTitle: MobileElement

    @AndroidFindBy(xpath = "//*[@text='Drag']")
    @iOSFindBy(id = "Drag")
    private lateinit var dragMenu: MobileElement

    @AndroidFindBy(xpath = "//*[@text='Login']")
    @iOSFindBy(id = "Login")
    private lateinit var loginMenu: MobileElement


    override fun isPageDisplayed(): Boolean? {
        return isElementDisplayed(txtTitle)
    }

    override fun open(): HomePage {
        resetApplication()
        removeApp()
        launchApp()
        return this
    }

    fun waitForPageDisplayed(): HomePage{
        waitForElementDisplay(txtTitle)
        return this
    }

    fun clickItemMenu(menu: String): HomePage{
        when(menu){
            "Drag" -> dragMenu.click()
            "Login" -> loginMenu.click()
        }
        return this
    }
}
