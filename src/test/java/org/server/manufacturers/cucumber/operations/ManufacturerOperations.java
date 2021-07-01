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

package org.server.manufacturers.cucumber.operations;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import kong.unirest.HttpResponse;
import lombok.extern.slf4j.Slf4j;
import org.server.manufacturers.cucumber.client.ManufacturerClient;
import org.server.manufacturers.dto.ManufacturerDTO;
import org.server.manufacturers.dto.ManufacturerListResponse;
import org.server.manufacturers.dto.ManufacturerResponse;
import org.server.manufacturers.dto.UpdateManufacturerDTORequest;

import java.io.IOException;
import java.util.List;

@Slf4j
public class ManufacturerOperations {

    public HttpResponse<String> createOrLoadManufacturers(List<ManufacturerDTO> creationDTO) {
        return ManufacturerClient.createManufacturer(creationDTO);
    }

    public ManufacturerResponse findManufacturer(Long id) throws IOException {
        return new ObjectMapper().readValue(ManufacturerClient.findManufacturerById(id).getBody().getObject().toString(), ManufacturerResponse.class);
    }

    public HttpResponse<String> updateManufacturer(Long id, UpdateManufacturerDTORequest updateRequest) {
        return ManufacturerClient.updateManufacturer(id, updateRequest);
    }

    public void deleteManufacturer(Long id) {
        HttpResponse<?> response = ManufacturerClient.deleteManufacturer(id);
    }

    public ManufacturerListResponse findAllManufacturers(int pageNo, int pageSize, String direction, String fieldName) throws IOException {
        return ManufacturerClient.listManufacturers(pageNo, pageSize, direction, fieldName);
    }

    public List<ManufacturerResponse> searchManufacturers(String searchParam) throws JsonProcessingException {
        return new ObjectMapper().readValue(ManufacturerClient.searchManufacturers(searchParam).getBody().getArray().toString(), new TypeReference<List<ManufacturerResponse>>(){});
    }
}
