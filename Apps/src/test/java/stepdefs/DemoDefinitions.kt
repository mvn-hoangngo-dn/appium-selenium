package stepdefs

import base.BaseDefinitions
import core.PageFactory
import org.testng.Assert
import pages.appPages.DragPage
import pages.appPages.HomePage
import pages.webPages.HomePortalPage
import pages.webPages.LoginPage


class DemoDefinitions : BaseDefinitions() {

    private lateinit var loginPage: LoginPage
    private lateinit var homePortalPage: HomePortalPage
    private val homePage: HomePage? = PageFactory(HomePage::class.java).create()
    private val dragPage: DragPage? = PageFactory(DragPage::class.java).create()


    init {
        Given("Open [Login] screen of Portal") {
            getWebDriver()?.let {
                loginPage = LoginPage(it)
                loginPage.open()
            }
        }
        And("^Login successfully with email \"([^\"]*)\" and password \"([^\"]*)\"$") { email: String, password: String ->
            loginPage.inputEmail(email).inputPassword(password).clickLoginBtn()
        }
        Then("Move to [Home Portal] screen successfully") {
            Thread.sleep(5000)
            getWebDriver()?.let {
                homePortalPage = HomePortalPage(it)
                Assert.assertTrue(homePortalPage.waitForPageDisplayed().isPageDisplayed() ?: false)
            }
            Thread.sleep(5000)
        }
        Given("Open App") {
            homePage?.open()
        }
        Then("Move to [Home Page] screen successfully") {
            Assert.assertTrue(homePage?.isPageDisplayed()?: false)
        }
        When("Click Drag menu") {
            homePage?.clickDragMenu()
        }
        And("Move to [Drag] screen successfully") {
            Assert.assertTrue(dragPage?.isPageDisplayed()?: false)
        }
        And("Drag and drop image") {
            dragPage?.dragAndDrop()
        }
        And("Quit browser") {
            quitWebDriver()
        }
    }
}
