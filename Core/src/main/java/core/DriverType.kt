package core

import io.github.bonigarcia.wdm.WebDriverManager
import org.openqa.selenium.chrome.ChromeDriver
import org.openqa.selenium.chrome.ChromeOptions
import org.openqa.selenium.firefox.FirefoxDriver
import org.openqa.selenium.firefox.FirefoxOptions
import org.openqa.selenium.remote.DesiredCapabilities
import org.openqa.selenium.remote.RemoteWebDriver

enum class DriverType : DriverSetup {
    FIREFOX {
        override fun getWebDriverObject(capabilities: DesiredCapabilities?, isHeadLess: Boolean): RemoteWebDriver {
            WebDriverManager.firefoxdriver().setup()
            val options = FirefoxOptions()
            options.merge(capabilities)
            options.setHeadless(HEADLESS)
            if (isHeadLess) {
                options.addArguments("--headless")
                options.addArguments("--window-size=1920,1080")
            }
            return FirefoxDriver(options)
        }
    },
    CHROME {
        override fun getWebDriverObject(capabilities: DesiredCapabilities?, isHeadLess: Boolean): RemoteWebDriver {
            WebDriverManager.chromedriver().setup()
            val chromePreferences = HashMap<String, Any>()
            chromePreferences["profile.password_manager_enabled"] = false
            val options = ChromeOptions()
            options.merge(capabilities)
            options.setHeadless(HEADLESS)
            options.addArguments("--no-default-browser-check")
            if (isHeadLess) {
                options.addArguments("--headless")
                options.addArguments("--window-size=1920,1080")
            }
            options.setExperimentalOption("prefs", chromePreferences)
            return ChromeDriver(options)
        }
    };

    override fun toString(): String {
        return super.toString().lowercase()
    }

    companion object {
        val HEADLESS = java.lang.Boolean.getBoolean("headless")
    }
}
