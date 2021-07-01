Feature: Create, find, update and delete Manufacturer

  Scenario: Create a Manufacturer
    Given A manufacturer
      | country | mfrCommonName | mfrId | mfrName    | isPrimary | name          |
      | Germany | BMW           | 967   | BMW M GMBH | true      | Passenger Car |
    When user creates the manufacturer
    Then A "Successfully Created" message should be received

  Scenario: Find Manufacturer
    Given a manufacturer ID 3
    When user searches for manufacturer
    Then the following manufacturer must be returned
      | id | country | mfrCommonName | mfrId | mfrName    | isPrimary | name          |
      | 3  | Germany | BMW           | 967   | BMW M GMBH | true      | Passenger Car |

  Scenario: Update Manufacturer
    Given a manufacturer with ID 3
    When user updates manufacturer by adding a new Vehicle Type
      | id | country | mfrCommonName | mfrId | mfrName    | isPrimary   | name                                                |
      | 3  | Germany | BMW           | 967   | BMW M GMBH | true, false | Passenger Car, Multipurpose Passenger Vehicle (MPV) |
    Then The manufacturer should be updated with "Successfully Updated" message