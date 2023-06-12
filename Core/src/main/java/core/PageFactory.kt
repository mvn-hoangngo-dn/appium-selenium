package core

import io.appium.java_client.ios.IOSDriver

/**
 * @author tien.hoang
 */
class PageFactory<T>(private val clazz: Class<T>) {
    fun create(): T? {
        val platform: String = if (AppiumController.instance.getDriver() is IOSDriver<*>) {
            "IOS"
        } else {
            "Android"
        }
        try {
            var newClazz: Class<*>? = null
            try {
                newClazz = Class.forName(clazz.name + platform)
            } catch (e: Exception) {
                //No-opt
            }
            if (newClazz == null) {
                newClazz = clazz
            }
            val classHash = AppiumController.instance.getDriver().hashCode().toString() + Class.forName(clazz.name)
            if (pages.containsKey(classHash)) {
                return pages[classHash] as T?
            }
            val constructor = newClazz.getConstructor()
            val `object` = constructor.newInstance()
            pages[classHash] = `object`
            return `object` as T
        } catch (e: Exception) {
            e.printStackTrace()
        }
        return null
    }


    companion object {
        private val pages: MutableMap<String, Any> = HashMap()
    }
}