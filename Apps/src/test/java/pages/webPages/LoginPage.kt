package pages.webPages

import base.BasePage
import ml.utils.Constant
import org.openqa.selenium.By
import org.openqa.selenium.WebElement
import org.openqa.selenium.remote.RemoteWebDriver
import org.openqa.selenium.support.FindBy

class LoginPage(private val driver: RemoteWebDriver) : BasePage<LoginPage>() {

    @FindBy(css = "input[type=email]")
    private lateinit var inputEmail: WebElement

    @FindBy(css = "input[type=password]")
    private lateinit var inputPassword: WebElement

    @FindBy(className = "btn")
    private lateinit var btnLogin: WebElement

    override fun open(): LoginPage {
        driver.get(Constant.LOGIN_PAGE_URL)
        waitForWebElementDisplay(inputEmail)
        return this
    }

    override fun isPageDisplayed(): Boolean? {
        return isWebElementDisplayed(inputEmail)
    }

    fun waitForPageDisplayed(): LoginPage {
        waitForWebElementDisplay(btnLogin)
        return this
    }

    fun inputEmail(email: String): LoginPage {
        driver.findElement(By.cssSelector("input[type=email]")).sendKeys(email)
//        inputEmail.sendKeys(email)
        return this
    }

    fun inputPassword(password: String): LoginPage {
        driver.findElement(By.cssSelector("input[type=password]")).sendKeys(password)
//        inputPassword.sendKeys(password)
        return this
    }

    fun clickLoginBtn(): LoginPage {
        driver.findElement(By.className("btn")).click()
//        btnLogin.click()
        return this
    }


}
