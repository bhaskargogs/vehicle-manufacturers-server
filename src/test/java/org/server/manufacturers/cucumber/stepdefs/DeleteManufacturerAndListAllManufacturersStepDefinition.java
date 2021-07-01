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
import io.cucumber.java.en.And;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import org.server.manufacturers.cucumber.dto.ManufacturerEntity;
import org.server.manufacturers.cucumber.operations.ManufacturerOperations;
import org.server.manufacturers.dto.ManufacturerListResponse;
import org.server.manufacturers.dto.ManufacturerResponse;

import java.io.IOException;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import static org.assertj.core.api.Assertions.assertThat;

public class DeleteManufacturerAndListAllManufacturersStepDefinition {

    Long id;
    ManufacturerOperations manufacturerOperations = new ManufacturerOperations();
    ManufacturerListResponse response;
    /*
    * Pagination attributes
    * */
    int pageNo;
    int pageSize;
    String direction;
    String fieldName;

    @Given("A manufacturer with ID {int}")
    public void aManufacturerWithID(int id) {
        this.id = (long) id;
    }

    @When("user deletes the manufacturer")
    public void userDeletesTheManufacturer() {
        manufacturerOperations.deleteManufacturer(id);
    }

    @And("user lists all manufacturers with the following attributes")
    public void userListsAllManufacturersWithTheFollowingAttributes(DataTable table) throws IOException {
        for (Map<String, String> data: table.asMaps()) {
            pageNo = Integer.parseInt(data.get("pageNo"));
            pageSize = Integer.parseInt(data.get("pageSize"));
            direction = data.get("direction");
            fieldName = data.get("fieldName");
        }
        response = manufacturerOperations.findAllManufacturers(pageNo, pageSize, direction, fieldName);
    }

    @Then("the total manufacturers are {long}")
    public void theTotalManufacturersAre(long total) {
        assertThat(response.getTotalManufacturers()).isEqualTo(total);
    }

    @And("the manufacturers are")
    public void theManufacturersAre(DataTable expected) {
        List<ManufacturerResponse> expectedManufacturersList = expected.asMaps().stream()
                .map(ManufacturerEntity::createManufacturerResponse).collect(Collectors.toList());
        assertThat(response.getManufacturersList()).isEqualTo(expectedManufacturersList);
    }
}
