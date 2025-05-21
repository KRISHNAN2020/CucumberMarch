Feature: An example

  Scenario: The example
    Given an example scenario
    When all step definitions are implemented
    Then the scenario passes


  Scenario: Launch a webpage and verify its title
    Given I launch the Chrome browser
    When I navigate to "https://www.flipkart.com/"
    Then the page title should be "Online Shopping Site for Mobiles, Electronics, Furniture, Grocery, Lifestyle, Books & More. Best Offers!"
    And take screenshot of the Entire Page

  Scenario: Verify user exists in the database
    Given I connect to the database
    Then I verify the user with email "john.doe@example.com" exists in the database
    Then I close the database connection




