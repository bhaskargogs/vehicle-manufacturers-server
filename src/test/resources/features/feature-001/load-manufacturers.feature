Feature: Load Manufacturers

  Scenario: Loading 2 manufacturers with vehicle types
    Given a manufacturer list
      | country             | mfrCommonName | mfrId | mfrName                 | isPrimary          | name                                                |
      | United States (USA) | Ford          | 976   | Ford Motor Company, USA | true, false        | Passenger Car, Multipurpose Passenger Vehicle (MPV) |
      | United States (USA) | Optima        | 1044  | Optima Bus Corp         | true, false, false | Bus, Truck, Multipurpose Passenger Vehicle (MPV)    |


