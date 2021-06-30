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

import kong.unirest.HttpResponse;
import lombok.extern.slf4j.Slf4j;
import org.server.manufacturers.cucumber.client.ManufacturerClient;
import org.server.manufacturers.dto.ManufacturerDTO;

import java.util.List;

@Slf4j
public class ManufacturerOperations {

    public HttpResponse<String> createOrLoadManufacturers(List<ManufacturerDTO> creationDTO) {
        return ManufacturerClient.createManufacturer(creationDTO);
    }
}
