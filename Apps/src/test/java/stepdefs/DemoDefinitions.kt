package stepdefs

import base.BaseDefinitions
import core.PageFactory
import org.testng.Assert
import pages.appPages.DragPage
import pages.appPages.HomePage
import pages.appPages.LoginSignupPage
import pages.webPages.HomePortalPage
import pages.webPages.LoginPage


class DemoDefinitions : BaseDefinitions() {

    private lateinit var loginPage: LoginPage
    private lateinit var homePortalPage: HomePortalPage
    private val homePage: HomePage? = PageFactory(HomePage::class.java).create()
    private val dragPage: DragPage? = PageFactory(DragPage::class.java).create()
    private val loginSignupPage: LoginSignupPage? = PageFactory(LoginSignupPage::class.java).create()


    init {
        Given("Open [Login] screen of Web Portal") {
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
            Thread.sleep(2000)
            Assert.assertTrue(homePage?.waitForPageDisplayed()?.isPageDisplayed()?: false)
        }
        When("^Click ([^\"]*) menu$") { item: String ->
            homePage?.clickItemMenu(item)
        }
        And("Move to [Drag] screen successfully") {
            Assert.assertTrue(dragPage?.waitForPageDisplayed()?.isPageDisplayed()?: false)
        }
        And("Drag and drop image") {
            dragPage?.dragAndDrop()
        }
        And("Quit browser") {
            quitWebDriver()
        }
        And("Move to [Login] screen successfully") {
            Assert.assertTrue(loginSignupPage?.waitForPageDisplayed()?.isPageDisplayed()?: false)
        }
        And("^Login mobile app with email \"([^\"]*)\" and password \"([^\"]*)\"$") { email: String, password: String ->
            loginSignupPage?.inputEmailAndPassword(email,password)?.clickBtnLogin()
        }
        Then("Login successfully") {
            Assert.assertTrue(loginSignupPage?.isPopUpSuccessDisplayed()?: false)
            Thread.sleep(2000)
            loginSignupPage?.clickOkPopUp()
        }
    }
}
