package core

import io.appium.java_client.AppiumDriver

/**
 * @author tien.hoang
 */
abstract class AppiumBaseClass {
    protected fun driver(): AppiumDriver<*>? {
        return AppiumController.instance.getDriver()
    }
}