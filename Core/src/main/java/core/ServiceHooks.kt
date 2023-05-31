package core

import org.junit.Before

/**
 * @author tien.hoang
 */
class ServiceHooks {
    @Before
    fun initializeTest() {
        if (isFirstStep) {
            Runtime.getRuntime().addShutdownHook(Thread { afterAll() })
            isFirstStep = false
        }
    }

    private fun afterAll() {
        AppiumController.instance.stop()
    }

    companion object {
        private var isFirstStep = true
    }
}