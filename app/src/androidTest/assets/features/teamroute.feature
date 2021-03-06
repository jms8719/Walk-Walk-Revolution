Feature: View teammate routes

  Scenario: No teammate routes
    Given a routes activity
    When the user clicks the team button
    Then the list is empty

  Scenario: Teammate routes exists
    Given a routes activity
    And there's a route with the title BL's_Route
    When the user clicks the team button
    Then the list contains a route with the title BL's_Route

  Scenario: Overwrite Teammate Routes
    Given a routes activity
    And the user walked 500 steps with BL's_Route
    And there's a route with the title BL's_Route
    When the user clicks the team button
    Then the list contains a route with the title BL's_Route
    And the list contains a route with 500 steps

  @skipAndroid
  Scenario: we gonna skip this one for android
    Given whatever setup
    When whatever event
    Then whatever assert