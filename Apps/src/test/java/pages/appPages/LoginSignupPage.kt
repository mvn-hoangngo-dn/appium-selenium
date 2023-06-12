package pages.appPages

import base.BasePage
import io.appium.java_client.MobileElement
import io.appium.java_client.pagefactory.AndroidFindBy
import io.appium.java_client.pagefactory.iOSFindBy

class LoginSignupPage : BasePage<LoginSignupPage>() {

    @AndroidFindBy(xpath = "//*[@text='Login / Sign up Form']")
    @iOSFindBy(id = "Login / Sign up Form")
    private lateinit var txtTitle: MobileElement

    @AndroidFindBy(accessibility = "input-email")
    @iOSFindBy(accessibility = "input-email")
    private lateinit var edtEmail: MobileElement

    @AndroidFindBy(accessibility = "input-password")
    @iOSFindBy(accessibility = "input-password")
    private lateinit var edtPassword: MobileElement

    @AndroidFindBy(accessibility = "button-LOGIN")
    @iOSFindBy(accessibility = "button-LOGIN")
    private lateinit var btnLogin: MobileElement

    @AndroidFindBy(xpath = "//*[@text='OK']")
    @iOSFindBy(xpath = "//*[@name='OK']")
    private lateinit var btnOkPopup: MobileElement

    @AndroidFindBy(xpath = "//*[@text='Success']")
    @iOSFindBy(xpath = "//*[@name='Success']")
    private lateinit var txtTitlePopup: MobileElement


    override fun isPageDisplayed(): Boolean? {
        return isElementDisplayed(txtTitle)
    }

    override fun open(): LoginSignupPage {
        return this
    }

    fun waitForPageDisplayed(): LoginSignupPage {
        waitForElementDisplay(txtTitle)
        return this
    }

    fun inputEmailAndPassword(email: String, password: String): LoginSignupPage {
        edtEmail.sendKeys(email)
        edtPassword.sendKeys(password)
        return this
    }

    fun clickBtnLogin() = btnLogin.click()

    fun clickOkPopUp() = btnOkPopup.click()

    fun isPopUpSuccessDisplayed(): Boolean? {
        waitForElementDisplay(btnOkPopup)
        return isElementDisplayed(btnOkPopup)
    }

    fun getTxtTitlePopup(): String = txtTitlePopup.text

}
