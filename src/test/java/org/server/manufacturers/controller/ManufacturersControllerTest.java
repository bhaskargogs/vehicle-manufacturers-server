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

package org.server.manufacturers.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.server.manufacturers.dto.ManufacturerDTO;
import org.server.manufacturers.dto.ManufacturerResponse;
import org.server.manufacturers.dto.UpdateManufacturerDTORequest;
import org.server.manufacturers.dto.VehicleTypesDTO;
import org.server.manufacturers.exception.InvalidConstraintException;
import org.server.manufacturers.exception.NotFoundException;
import org.server.manufacturers.service.ManufacturerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import static org.mockito.ArgumentMatchers.*;
import static org.mockito.BDDMockito.*;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@ExtendWith(SpringExtension.class)
@WebMvcTest(ManufacturerController.class)
public class ManufacturersControllerTest {

    @Autowired
    MockMvc mockMvc;

    @MockBean
    private ManufacturerService manufacturerService;

    @Test
    public void findManufacturer_ReturnsManufacturerDetails() throws Exception {
        given(manufacturerService.findById(1L))
                .willReturn(new ManufacturerResponse(1L, "Japan", "Mazda", "Mazda Motor Corporation", 1041L,
                        Collections.singletonList(new VehicleTypesDTO(true, "Passenger Car"))));

        mockMvc.perform(MockMvcRequestBuilders.get("/manufacturers/1")
                .accept(MediaType.APPLICATION_JSON)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("country").value("Japan"))
                .andExpect(jsonPath("mfrCommonName").value("Mazda"))
                .andExpect(jsonPath("mfrId").value(1041L))
                .andExpect(jsonPath("mfrName").value("Mazda Motor Corporation"))
                .andExpect(jsonPath("vehicleTypes").isArray())
                .andExpect(jsonPath("$.vehicleTypes[0].name").value("Passenger Car"))
                .andExpect(jsonPath("$.vehicleTypes[0].isPrimary").value(true));
    }

    @Test
    public void findManufacturer_NotFound() throws Exception {
        given(manufacturerService.findById(1L)).willThrow(new NotFoundException());

        mockMvc.perform(MockMvcRequestBuilders.get("/manufacturers/1")
                .accept(MediaType.APPLICATION_JSON)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound());
    }

    @Test
    public void createManufacturer_ReturnsSuccessMessage() throws Exception {
        ManufacturerDTO manufacturerDTO = new ManufacturerDTO("Japan", "Mazda", "Mazda Motor Corporation", 1041L,
                Collections.singletonList(new VehicleTypesDTO(true, "Passenger Car")));

        given(manufacturerService.create(anyList())).willReturn("Successfully Created");

        mockMvc.perform(MockMvcRequestBuilders.post("/manufacturers")
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
                .content(new ObjectMapper().writeValueAsString(Collections.singletonList(manufacturerDTO))))
                .andExpect(status().isCreated())
                .andExpect(content().string("Successfully Created"));
    }

    @Test
    public void createManufacturer_InvalidConstraint() throws Exception {
        given(manufacturerService.create(anyList())).willThrow(new InvalidConstraintException());

        mockMvc.perform(MockMvcRequestBuilders.post("/manufacturers")
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
                .content(new ObjectMapper().writeValueAsString(Collections.singletonList(any(ManufacturerDTO.class)))))
                .andExpect(status().isBadRequest());
    }

    @Test
    public void createManufacturers_ReturnsSuccessfulLoadedMessage() throws Exception {
        ManufacturerDTO manufacturerDTO = new ManufacturerDTO("Japan", "Mazda", "Mazda Motor Corporation", 1041L,
                Collections.singletonList(new VehicleTypesDTO(true, "Passenger Car")));
        ManufacturerDTO manufacturerDTO2 = new ManufacturerDTO("United States (USA)", "Ford", "Ford Motor Corporation", 1095L,
                Collections.singletonList(new VehicleTypesDTO(false, "Multipurpose Passenger Vehicle (MPV)")));

        given(manufacturerService.create(anyList())).willReturn("Successfully Loaded Manufacturers");

        mockMvc.perform(MockMvcRequestBuilders.post("/manufacturers")
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
                .content(new ObjectMapper().writeValueAsString(Arrays.asList(manufacturerDTO, manufacturerDTO2))))
                .andExpect(status().isCreated())
                .andExpect(content().string("Successfully Loaded Manufacturers"));
    }

    @Test
    public void createManufacturers_InvalidConstraint() throws Exception {
        given(manufacturerService.create(anyList())).willThrow(new InvalidConstraintException());

        mockMvc.perform(MockMvcRequestBuilders.post("/manufacturers")
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
                .content(new ObjectMapper().writeValueAsString(anyList())))
                .andExpect(status().isBadRequest());
    }

    @Test
    public void updateManufacturer_ReturnsSuccessMessage() throws Exception {
        UpdateManufacturerDTORequest updatedManufactureDTORequest = new UpdateManufacturerDTORequest(1L, "Japan", "Mazda", "Mazda Motor Corporation", 1041L,
                Collections.singletonList(new VehicleTypesDTO(true, "Passenger Car")));

        given(manufacturerService.updateManufacturer(anyLong(), any(UpdateManufacturerDTORequest.class))).willReturn("Successfully Updated");

        mockMvc.perform(MockMvcRequestBuilders.put("/manufacturers/1")
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
                .content(new ObjectMapper().writeValueAsString(updatedManufactureDTORequest)))
                .andExpect(status().isOk())
                .andExpect(content().string("Successfully Updated"));
    }

    @Test
    public void updateManufacturer_NotFound() throws Exception {
        UpdateManufacturerDTORequest updatedManufactureDTORequest = new UpdateManufacturerDTORequest(1L, "Japan", "Mazda", "Mazda Motor Corporation", 1041L,
                Collections.singletonList(new VehicleTypesDTO(true, "Passenger Car")));

        given(manufacturerService.updateManufacturer(anyLong(), any(UpdateManufacturerDTORequest.class))).willThrow(new NotFoundException());

        mockMvc.perform(MockMvcRequestBuilders.put("/manufacturers/1")
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
                .content(new ObjectMapper().writeValueAsString(updatedManufactureDTORequest)))
                .andExpect(status().isNotFound());
    }

    @Test
    public void updateManufacturer_InvalidConstraints() throws Exception {
        UpdateManufacturerDTORequest updatedManufactureDTORequest = new UpdateManufacturerDTORequest(1L, "Japan", "Mazda", "Mazda Motor Corporation", 1041L,
                Collections.singletonList(new VehicleTypesDTO(true, "Passenger Car")));

        given(manufacturerService.updateManufacturer(anyLong(), any(UpdateManufacturerDTORequest.class))).willThrow(new InvalidConstraintException());

        mockMvc.perform(MockMvcRequestBuilders.put("/manufacturers/1")
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
                .content(new ObjectMapper().writeValueAsString(updatedManufactureDTORequest)))
                .andExpect(status().isBadRequest());
    }

    @Test
    public void deleteManufacturer_deletesManufacturer() throws Exception {
        ManufacturerDTO manufacturerDTO = new ManufacturerDTO("Japan", "Mazda", "Mazda Motor Corporation", 1041L,
                Collections.singletonList(new VehicleTypesDTO(true, "Passenger Car")));
        willDoNothing().given(manufacturerService).deleteManufacturer(anyLong());

        mockMvc.perform(MockMvcRequestBuilders.delete("/manufacturers/1")
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());

        verify(manufacturerService, times(1)).deleteManufacturer(1L);
    }

    @Test
    public void deleteManufacturer_NotFound() throws Exception {
        willThrow(new NotFoundException()).given(manufacturerService).deleteManufacturer(anyLong());

        mockMvc.perform(MockMvcRequestBuilders.delete("/manufacturers/1")
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound());
    }

    @Test
    public void findAllManufacturers_ListsManufacturers() throws Exception {
        ManufacturerResponse manufacturerDTO = new ManufacturerResponse(1L, "Japan", "Mazda", "Mazda Motor Corporation", 1041L,
                Collections.singletonList(new VehicleTypesDTO(true, "Passenger Car")));
        ManufacturerResponse manufacturerDTO2 = new ManufacturerResponse(2L, "United States (USA)", "Ford", "Ford Motor Corporation", 1095L,
                Collections.singletonList(new VehicleTypesDTO(false, "Multipurpose Passenger Vehicle (MPV)")));
        List<ManufacturerResponse> manufacturers = Arrays.asList(manufacturerDTO, manufacturerDTO2);

        given(manufacturerService.findAllManufacturers()).willReturn(manufacturers);

        mockMvc.perform(MockMvcRequestBuilders.get("/manufacturers")
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].country").value("Japan"))
                .andExpect(jsonPath("$[0].mfrCommonName").value("Mazda"))
                .andExpect(jsonPath("$[1].country").value("United States (USA)"))
                .andExpect(jsonPath("$[1].mfrCommonName").value("Ford"));
    }

    @Test
    public void findAllManufacturers_EmptyList() throws Exception {
        given(manufacturerService.findAllManufacturers()).willReturn(new ArrayList<>());

        mockMvc.perform(MockMvcRequestBuilders.get("/manufacturers")
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().string("[]"));
    }

    @Test
    public void searchManufacturers_ReturnsManufacturers() throws Exception {
        ManufacturerResponse manufacturerDTO = new ManufacturerResponse(1L, "GERMANY", "BMW", "BMW AG", 966L, null);
        ManufacturerResponse manufacturerDTO2 = new ManufacturerResponse(1L, "GERMANY", "BMW", "BMW M GMBH", 967L,
                Collections.singletonList(new VehicleTypesDTO(true, "Passenger Car")));

        List<ManufacturerResponse> manufacturers = Arrays.asList(manufacturerDTO, manufacturerDTO2);

        given(manufacturerService.searchManufacturers(anyString())).willReturn(manufacturers);

        mockMvc.perform(MockMvcRequestBuilders.get("/manufacturers/search")
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
                .param("searchParam", "bmw"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].country").value("GERMANY"))
                .andExpect(jsonPath("$[0].mfrName").value("BMW AG"))
                .andExpect(jsonPath("$[1].country").value("GERMANY"))
                .andExpect(jsonPath("$[1].mfrName").value("BMW M GMBH"));;
    }

    @Test
    public void searchManufacturers_EmptyList() throws Exception {
        given(manufacturerService.searchManufacturers(anyString())).willReturn(new ArrayList<>());

        mockMvc.perform(MockMvcRequestBuilders.get("/manufacturers/search")
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
                .param("searchParam", "bmw"))
                .andExpect(status().isOk())
                .andExpect(content().string("[]"));
    }

}
