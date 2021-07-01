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

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import kong.unirest.HttpResponse;
import kong.unirest.JsonNode;
import kong.unirest.Unirest;
import lombok.extern.slf4j.Slf4j;
import org.server.manufacturers.dto.ManufacturerDTO;
import org.server.manufacturers.dto.ManufacturerListResponse;
import org.server.manufacturers.dto.UpdateManufacturerDTORequest;
import org.server.manufacturers.exception.InvalidConstraintException;
import org.server.manufacturers.exception.NotFoundException;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;

import java.io.IOException;
import java.util.List;

@Slf4j
public abstract class ManufacturerClient {

    private static final String URL = "http://localhost:8081/manufacturers/";

    public static HttpResponse<String> createManufacturer(List<ManufacturerDTO> manufacturerDTOS) {
        HttpResponse<String> response;

        log.info(String.format("ManufacturerClient.createManufacturer(): [%s]", manufacturerDTOS.toString()));
        try {
            response = Unirest.post(URL)
                    .header(HttpHeaders.ACCEPT, MediaType.APPLICATION_JSON_VALUE)
                    .header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)
                    .body(new ObjectMapper().writeValueAsString(manufacturerDTOS))
                    .asString();

            log.info(String.format("ManufacturerClient.createManufacturer(): [%s]", response.getBody()));

        } catch (InvalidConstraintException | JsonProcessingException ex) {
            throw new InvalidConstraintException();
        }
        return response;
    }

    public static HttpResponse<JsonNode> searchManufacturers(String searchParam) {
        return Unirest.get(URL + "search")
                .header(HttpHeaders.ACCEPT, MediaType.APPLICATION_JSON_VALUE)
                .header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)
                .queryString("searchParam", searchParam)
                .asJson();
    }

    public static ManufacturerListResponse listManufacturers(int pageNumber, int pageSize, String direction, String sortField) throws IOException {
        ManufacturerListResponse responseDTO = new ManufacturerListResponse();

        HttpResponse<JsonNode> response = Unirest.get(URL)
                .header("accept", "application/json")
                .queryString("pageNo", pageNumber)
                .queryString("pageSize", pageSize)
                .queryString("direction", direction)
                .queryString("sort", sortField)
                .asJson();

        if (response.isSuccess()) {
            responseDTO = new ObjectMapper().readValue(response.getBody().getObject().toString(), ManufacturerListResponse.class);
            log.info(String.format("ManufacturerClient.listManufacturers(): ManufacturerDTO = [%s]", responseDTO.getManufacturersList().toString()));
        }

        return responseDTO;
    }

    public static HttpResponse<String> updateManufacturer(Long id, UpdateManufacturerDTORequest updateRequest) {
        HttpResponse<String> response;
        try {
            response = Unirest.put(URL + id)
                    .header(HttpHeaders.ACCEPT, MediaType.APPLICATION_JSON_VALUE)
                    .header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)
                    .body(new ObjectMapper().writeValueAsString(updateRequest))
                    .asString();

            log.info(String.format("ManufacturerClient.updateManufacturer(): [%s]", response.getBody()));

        } catch (NotFoundException | JsonProcessingException ex) {
            throw new NotFoundException();
        }

        return response;
    }

    public static HttpResponse<JsonNode> findManufacturerById(Long id) throws IOException {
        HttpResponse<JsonNode> response;
        try {
            response = Unirest.get(URL + id)
                    .header(HttpHeaders.ACCEPT, MediaType.APPLICATION_JSON_VALUE)
                    .header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)
                    .asJson();
        } catch (NotFoundException ex) {
            throw new NotFoundException();
        }

        return response;
    }

    public static HttpResponse<?> deleteManufacturer(Long id) {
        return Unirest.delete(URL + id)
                .header(HttpHeaders.ACCEPT, MediaType.APPLICATION_JSON_VALUE)
                .header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE).asEmpty();
    }
}
