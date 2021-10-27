Feature: Testing Sofnium TV page

  @tv
  Scenario: Test controls TV page
    Given I validate label "Continua viendo"
    When I pause the player
    Then I navigate between the movies