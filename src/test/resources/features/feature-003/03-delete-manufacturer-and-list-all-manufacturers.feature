Feature: Delete Manufacturer and List Manufacturers

  Scenario: Delete Manufacturer and List All Manufacturers
    Given A manufacturer with ID 3
    When user deletes the manufacturer
    And user lists all manufacturers with the following attributes
      | pageNo | pageSize | direction | fieldName |
      | 0      | 20       | asc       | id        |

    Then the total manufacturers are 2
    And the manufacturers are
      | id | country             | mfrCommonName | mfrId | mfrName                 | isPrimary          | name                                                |
      | 1  | United States (USA) | Ford          | 976   | Ford Motor Company, USA | true, false        | Passenger Car, Multipurpose Passenger Vehicle (MPV) |
      | 2  | United States (USA) | Optima        | 1044  | Optima Bus Corp         | true, false, false | Bus, Truck, Multipurpose Passenger Vehicle (MPV)    |