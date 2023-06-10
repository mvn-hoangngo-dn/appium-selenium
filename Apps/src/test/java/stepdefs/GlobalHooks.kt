//package stepdefs
//
//import base.BaseDefinitions
//import io.cucumber.java8.Scenario
//
//class GlobalHooks : BaseDefinitions() {
//
//    init {
//        Before(0) { _: Scenario ->
//            // Run with junit
//        }
//
//        After(0) { scenario: Scenario ->
//            if (scenario.isFailed) {
//                println("Taking screenshots...")
//                embedScreenshot(scenario)
//            }
//        }
//    }
//}
