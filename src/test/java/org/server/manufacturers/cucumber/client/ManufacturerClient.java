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

package org.server.manufacturers.cucumber.client;

import kong.unirest.HttpResponse;
import kong.unirest.Unirest;
import lombok.extern.slf4j.Slf4j;
import org.server.manufacturers.dto.ManufacturerDTO;
import org.server.manufacturers.exception.InvalidConstraintException;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;

import java.util.List;

@Slf4j
public abstract class ManufacturerClient {
    private static final String MANUFACTURER_URI = "http://localhost:8081/manufacturers";

    public static HttpResponse<String> createManufacturers(List<ManufacturerDTO> manufacturerDTOS) {
        HttpResponse<String> response;

        try {
            response = Unirest.post(MANUFACTURER_URI)
                    .header(HttpHeaders.ACCEPT, MediaType.APPLICATION_JSON_VALUE)
                    .header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)
                    .body(manufacturerDTOS)
                    .asString();
//            log.info(String.format("ShipClient.createShip(): [%s]", response.getBody()));
        } catch (InvalidConstraintException ex) {
            throw new RuntimeException(ex);
        }
        return response;
    }

}
