@Demo

Feature: Demo login app and home Portal web

  @web
  Scenario: Demo Portal Web and Ios App
    Given Open [Login] screen of Web Portal
    And Login successfully with email "stg.dung.nguyen3@monstar-lab.com" and password "Abc123@@"
    Then Move to [Home Portal] screen successfully
    And Quit browser
  @ios
  Scenario: Demo Ios App
    Given Open App
    Then Move to [Home Page] screen successfully
    When Click Drag menu
    And Move to [Drag] screen successfully
    And Drag and drop image

    @android
  Scenario: Demo Portal Web and Android App
    Given Open App
    Then Move to [Home Page] screen successfully
    When Click Login menu
    And Move to [Login] screen successfully
    And Login mobile app with email "Hoang2210@gmail.com" and password "Abc123@@"
    Then Login successfully

   @combine
   Scenario: Demo Portal Web and Android App
     Given Open [Login] screen of Web Portal
     And Login successfully with email "stg.dung.nguyen3@monstar-lab.com" and password "Abc123@@"
     Then Move to [Home Portal] screen successfully
     And Quit browser
     Given Open App
     Then Move to [Home Page] screen successfully
     When Click Login menu
     And Move to [Login] screen successfully
     And Login mobile app with email "Hoang2210@gmail.com" and password "Abc123@@"
     Then Login successfully
