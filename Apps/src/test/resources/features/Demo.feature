@Demo

Feature: Demo login app and home Portal web

  Scenario: Login Portal successful
    Given Open [Login] screen of Portal
    And Login successfully with email "stg.dung.nguyen3@monstar-lab.com" and password "Abc123@@"
    Then Move to [Home Portal] screen successfully
    And Quit browser

  Scenario: Open App successful
    Given Open App
    Then Move to [Home Page] screen successfully
    When Click Drag menu
    And Move to [Drag] screen successfully
    And Drag and drop image
