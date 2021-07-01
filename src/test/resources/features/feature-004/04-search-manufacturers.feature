Feature: Search Manufacturers

  Scenario: Search manufacturers with search parameter
    Given A search parameter "Ford"
    When user searches for manufacturers with the parameter
    Then manufacturers list will be produced
      | id | country             | mfrCommonName | mfrId | mfrName                 | isPrimary   | name                                                |
      | 1  | United States (USA) | Ford          | 976   | Ford Motor Company, USA | true, false | Passenger Car, Multipurpose Passenger Vehicle (MPV) |