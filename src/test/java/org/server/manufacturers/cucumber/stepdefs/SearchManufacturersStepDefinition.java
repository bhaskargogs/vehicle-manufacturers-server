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

import com.fasterxml.jackson.core.JsonProcessingException;
import io.cucumber.datatable.DataTable;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import org.server.manufacturers.cucumber.dto.ManufacturerEntity;
import org.server.manufacturers.cucumber.operations.ManufacturerOperations;
import org.server.manufacturers.dto.ManufacturerResponse;

import java.util.List;
import java.util.stream.Collectors;

import static org.assertj.core.api.Assertions.assertThat;

public class SearchManufacturersStepDefinition {

    String param;
    ManufacturerOperations manufacturerOperations = new ManufacturerOperations();
    List<ManufacturerResponse> searchResponse;

    @Given("A search parameter {string}")
    public void aSearchParameter(String searchParam) {
        param = searchParam;
    }

    @When("user searches for manufacturers with the parameter")
    public void userSearchesForManufacturersWithTheParameter() throws JsonProcessingException {
        searchResponse = manufacturerOperations.searchManufacturers(param);
    }

    @Then("manufacturers list will be produced")
    public void manufacturersListWillBeProduced(DataTable expected) {
        List<ManufacturerResponse> expectedResponse = expected.asMaps().stream()
                .map(ManufacturerEntity::createManufacturerResponse).collect(Collectors.toList());
        assertThat(searchResponse).isEqualTo(expectedResponse);
    }
}
