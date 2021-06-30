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

import java.util.List;
import java.util.stream.Collectors;

import static org.assertj.core.api.Assertions.assertThat;

public class LoadManufacturersStepDefinition {

    List<ManufacturerDTO> manufacturersList;
    protected ManufacturerOperations manufacturerOperations = new ManufacturerOperations();
    HttpResponse<String> response;

    @Given("a manufacturers list")
    public void aManufacturerList(DataTable table) {
        manufacturersList = table.asMaps().stream().
                map(ManufacturerEntity::createManufacturerDTO).collect(Collectors.toList());
    }

    @When("a user loads the manufacturers list")
    public void aUserLoadsTheManufacturersList() {
        response = manufacturerOperations.createOrLoadManufacturers(manufacturersList);
    }

    @Then("the user receives a {string} response")
    public void theUserReceivesAResponse(String expected) {
        assertThat(expected).isEqualTo(response.getBody());
    }
}
