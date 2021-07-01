/*
 * Copyright 2021 Bhaskar Gogoi
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package org.server.manufacturers.cucumber.stepdefs;

import io.cucumber.datatable.DataTable;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import kong.unirest.HttpResponse;
import org.server.manufacturers.cucumber.dto.ManufacturerEntity;
import org.server.manufacturers.cucumber.operations.ManufacturerOperations;
import org.server.manufacturers.dto.ManufacturerDTO;
import org.server.manufacturers.dto.ManufacturerResponse;
import org.server.manufacturers.dto.UpdateManufacturerDTORequest;

import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;

import static org.assertj.core.api.Assertions.assertThat;

public class CreateFindAndUpdateManufacturerStepDefinition {

    /*
    * Create Manufacturer Attributes
    * */
    List<ManufacturerDTO> manufacturersList;
    protected ManufacturerOperations manufacturerOperations = new ManufacturerOperations();
    HttpResponse<String> response;


    /*
    * Find Manufacturer Attributes
    * */
    ManufacturerResponse manufacturerResponse;
    Long id;



    /*
    * Create Manufacturer Step Definition
    * */
    @Given("A manufacturer")
    public void aManufacturer(DataTable table) {
        manufacturersList = table.asMaps().stream().
                map(ManufacturerEntity::createManufacturerDTO).collect(Collectors.toList());
    }

    @When("user creates the manufacturer")
    public void userCreatesTheManufacturer() {
        response = manufacturerOperations.createOrLoadManufacturers(manufacturersList);
    }

    @Then("A {string} message should be received")
    public void aMessageShouldBeReceived(String expected) {
        assertThat(expected).isEqualTo(response.getBody());

    }


    /*
    * Find Manufacturer Step Definition
    * */
    @Given("a manufacturer ID {int}")
    public void aManufacturerID(int id) {
        this.id = (long) id;
    }

    @When("user searches for manufacturer")
    public void userSearchesForManufacturer() throws IOException {
        manufacturerResponse = manufacturerOperations.findManufacturer(id);
    }

    @Then("the following manufacturer must be returned")
    public void theFollowingManufacturerMustBeReturned(DataTable expected) {
        ManufacturerResponse expectedResponse = expected.asMaps().stream()
                .map(ManufacturerEntity::createManufacturerResponse).collect(Collectors.toList()).get(0);
        assertThat(manufacturerResponse).isEqualTo(expectedResponse);
    }


    /*
     * Update Manufacturer Step Definition
     * */
    @Given("a manufacturer with ID {int}")
    public void aManufacturerWithID(int id) {
        aManufacturerID(id);
    }

    @When("user updates manufacturer by adding a new Vehicle Type")
    public void userUpdatesManufacturerByAddingANewVehicleType(DataTable table) {
        UpdateManufacturerDTORequest updatedRequest = table.asMaps().stream()
                .map(ManufacturerEntity::createUpdateManufacturerDTO).collect(Collectors.toList()).get(0);
        response = manufacturerOperations.updateManufacturer(id, updatedRequest);
    }

    @Then("The manufacturer should be updated with {string} message")
    public void theManufacturerShouldBeUpdatedWithMessage(String expected) {
        assertThat(expected).isEqualTo(response.getBody());
    }

}
