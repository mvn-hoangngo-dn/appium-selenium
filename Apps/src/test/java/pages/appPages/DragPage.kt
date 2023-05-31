package pages.appPages

import base.BasePage
import base.PlatformTouchAction
import io.appium.java_client.MobileElement
import io.appium.java_client.pagefactory.AndroidFindBy
import io.appium.java_client.pagefactory.iOSFindBy
import io.appium.java_client.touch.WaitOptions
import io.appium.java_client.touch.offset.PointOption
import java.time.Duration

class DragPage : BasePage<DragPage>() {

    @AndroidFindBy(xpath = "//*[@text='Drag and Drop']")
    @iOSFindBy(id = "Drag and Drop")
    private lateinit var txtTitle: MobileElement

    @AndroidFindBy(accessibility = "drag-c1")
    @iOSFindBy(accessibility = "drag-c1")
    private lateinit var dragC1: MobileElement

    @AndroidFindBy(accessibility = "drop-c1")
    @iOSFindBy(accessibility = "drop-c1")
    private lateinit var dropC1: MobileElement

    @AndroidFindBy(accessibility = "drag-l1")
    @iOSFindBy(accessibility = "drag-l1")
    private lateinit var dragL1: MobileElement

    @AndroidFindBy(accessibility = "drop-l1")
    @iOSFindBy(accessibility = "drop-l1")
    private lateinit var dropL1: MobileElement

    @AndroidFindBy(accessibility = "drag-c3")
    @iOSFindBy(accessibility = "drag-c3")
    private lateinit var dragC3: MobileElement

    @AndroidFindBy(accessibility = "drop-c3")
    @iOSFindBy(accessibility = "drop-c3")
    private lateinit var dropC3: MobileElement

    override fun isPageDisplayed(): Boolean? {
        return isElementDisplayed(txtTitle)
    }

    fun waitForPageDisplayed(): DragPage {
        waitForElementDisplay(txtTitle)
        return this
    }

    override fun open(): DragPage {
        return this
    }

    fun dragAndDrop(): DragPage {
        dragAction(dragC1,dropC1)
        dragAction(dragL1,dropL1)
        dragAction(dragC3,dropC3)
        return this
    }

    private fun dragAction(e1: MobileElement, e2: MobileElement) {
        getDriver()?.let {
            PlatformTouchAction(it).longPress(PointOption.point(e1.location.x, e1.location.y))
                .waitAction(WaitOptions.waitOptions(Duration.ofSeconds(2)))
                .moveTo(PointOption.point(e2.location.x, e2.location.y)).release().perform()
        }
    }

}
