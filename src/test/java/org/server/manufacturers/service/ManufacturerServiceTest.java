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

package org.server.manufacturers.service;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.server.manufacturers.dto.ManufacturerDTO;
import org.server.manufacturers.dto.ManufacturerResponse;
import org.server.manufacturers.dto.UpdateManufacturerDTORequest;
import org.server.manufacturers.dto.VehicleTypesDTO;
import org.server.manufacturers.entity.Manufacturer;
import org.server.manufacturers.entity.VehicleTypes;
import org.server.manufacturers.exception.InvalidConstraintException;
import org.server.manufacturers.exception.NotFoundException;
import org.server.manufacturers.repository.ManufacturerRepository;
import org.server.manufacturers.util.CommonSpecifications;
import org.springframework.data.jpa.domain.Specification;

import java.util.*;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
public class ManufacturerServiceTest {

    @Mock
    private ManufacturerRepository manufacturerRepository;

    @InjectMocks
    private ManufacturerService manufacturerService;

    @Test
    public void getManufacturer_ReturnsManufacturerDetails() {
        Manufacturer manufacturer = new Manufacturer(1L, "Japan", "toyota", "Toyota Motor Corporation", 1057L, new ArrayList<>());

        given(manufacturerRepository.findById(1L)).willReturn(Optional.of(manufacturer));

        ManufacturerResponse newManufacturerDTO = manufacturerService.findById(1L);

        assertThat(newManufacturerDTO.getMfrCommonName()).isEqualTo("toyota");
        assertThat(newManufacturerDTO.getCountry()).isEqualTo("Japan");
        assertThat(newManufacturerDTO.getMfrId()).isEqualTo(1057L);
        assertThat(newManufacturerDTO.getMfrName()).isEqualTo("Toyota Motor Corporation");
        assertThat(newManufacturerDTO.getVehicleTypes()).isEmpty();
    }

    @Test
    public void getManufacturer_NotFound() {
        given(manufacturerRepository.findById(1L)).willThrow(new NotFoundException());

        assertThrows(NotFoundException.class, () -> manufacturerService.findById(1L));
    }

    @Test
    public void createManufacturer_ReturnSuccessMessage() {
        Manufacturer manufacturer = new Manufacturer(1L, "Japan", "toyota", "Toyota Motor Corporation", 1057L, new ArrayList<>());
        ManufacturerDTO createdManufactureDTO = new ManufacturerDTO("Japan", "toyota", "Toyota Motor Corporation", 1057L, new ArrayList<>());

        given(manufacturerRepository.save(any(Manufacturer.class))).willReturn(manufacturer);

        String response = manufacturerService.create(Collections.singletonList(createdManufactureDTO));
        assertThat(response).isEqualTo("Successfully Created");
    }

    @Test
    public void createManufacturer_InvalidConstraint() {
        Manufacturer manufacturer = new Manufacturer(1L, "Japan", "toyota", "Toyota Motor Corporation", 1057L, new ArrayList<>());
        ManufacturerDTO createdManufactureDTO = new ManufacturerDTO("Japan", "toyota09543_??-\\//", "Toyota Motor Corporation", 1057L, new ArrayList<>());

        given(manufacturerRepository.save(any(Manufacturer.class))).willThrow(new InvalidConstraintException());
        assertThrows(InvalidConstraintException.class, () -> manufacturerService.create(Collections.singletonList(createdManufactureDTO)));
    }

    @Test
    public void updateManufacturer_UpdatesManufacturer() {
        Manufacturer manufacturer = new Manufacturer(1L, "Japan", "toyota", "Toyota Motor Corporation", 1057L, new ArrayList<>());
        UpdateManufacturerDTORequest updateManufacturerRequest = new UpdateManufacturerDTORequest(1L, "Japan", "toyota", "Toyota Motor Corporation", 1057L, new ArrayList<>());

        given(manufacturerRepository.findById(anyLong())).willReturn(Optional.of(manufacturer));
        given(manufacturerRepository.save(any(Manufacturer.class))).willReturn(manufacturer);

        String response = manufacturerService.updateManufacturer(1L, updateManufacturerRequest);
        assertThat(response).isEqualTo("Successfully Updated");
    }

    @Test
    public void updateManufacturer_NotFound() {
        UpdateManufacturerDTORequest updateManufacturerRequest = new UpdateManufacturerDTORequest(1L, "Japan", "toyota", "Toyota Motor Corporation", 1057L, new ArrayList<>());

        given(manufacturerRepository.findById(anyLong())).willThrow(new NotFoundException());

        assertThrows(NotFoundException.class, () -> manufacturerService.updateManufacturer(1L, updateManufacturerRequest));
    }

    @Test
    public void updateManufacturer_InvalidConstraint() {
        Manufacturer manufacturer = new Manufacturer(1L, "Japan", "toyota", "Toyota Motor Corporation", 1057L, new ArrayList<>());
        UpdateManufacturerDTORequest updateManufacturerRequest = new UpdateManufacturerDTORequest(1L, "Japan", "toyota", "Toyota Motor Corporation", 1057L, new ArrayList<>());

        given(manufacturerRepository.findById(anyLong())).willReturn(Optional.of(manufacturer));
        given(manufacturerRepository.save(any(Manufacturer.class))).willThrow(new InvalidConstraintException());

        assertThrows(InvalidConstraintException.class, () -> manufacturerService.updateManufacturer(1L, updateManufacturerRequest));
    }

    @Test
    public void deleteManufacturer_deletesManufacturer() {
        Manufacturer manufacturer = new Manufacturer(1L, "Japan", "toyota", "Toyota Motor Corporation", 1057L, new ArrayList<>());
        given(manufacturerRepository.findById(1L)).willReturn(Optional.of(manufacturer));
        manufacturerService.deleteManufacturer(1L);

        verify(manufacturerRepository, times(1)).deleteById(1L);
    }

    @Test
    public void deleteManufacturer_NotFound() {
        given(manufacturerRepository.findById(anyLong())).willThrow(new NotFoundException());
        assertThrows(NotFoundException.class, () -> manufacturerService.deleteManufacturer(1L));
    }

    @Test
    public void findAllManufacturers_ReturnsManufacturers() {
        ManufacturerResponse manufacturerDTO1 = new ManufacturerResponse(1L,"Japan", "toyota", "Toyota Motor Corporation", 1057L, new ArrayList<>());
        ManufacturerResponse manufacturerDTO2 = new ManufacturerResponse(2L,"United States (USA)", "Ford", "Ford Motors USA", 1095L, Collections.singletonList(new VehicleTypesDTO(false, "Multipurpose Passenger Vehicle (MPV)")));
        Manufacturer manufacturer1 = new Manufacturer(1L, "Japan", "toyota", "Toyota Motor Corporation", 1057L, new ArrayList<>());
        Manufacturer manufacturer2 = new Manufacturer(2L, "United States (USA)", "Ford", "Ford Motors USA", 1095L, Collections.singletonList(new VehicleTypes(false, "Multipurpose Passenger Vehicle (MPV)")));

        List<ManufacturerResponse> manufacturerDTOs = Arrays.asList(manufacturerDTO1, manufacturerDTO2);
        List<Manufacturer> manufacturers = Arrays.asList(manufacturer1, manufacturer2);

        given(manufacturerRepository.findAll()).willReturn(manufacturers);

        assertThat(manufacturerService.findAllManufacturers()).isEqualTo(manufacturerDTOs);
    }

    @Test
    public void findAllManufacturers_EmptyList() {
        given(manufacturerRepository.findAll()).willReturn(new ArrayList<>());

        assertThat(manufacturerService.findAllManufacturers()).isEmpty();
    }

    @Test
    public void searchManufacturers_ReturnsManufacturers() {
        ManufacturerResponse manufacturerDTO = new ManufacturerResponse(1L, "GERMANY", "BMW", "BMW AG", 966L, null);
        ManufacturerResponse manufacturerDTO2 = new ManufacturerResponse(1L, "GERMANY", "BMW", "BMW M GMBH", 967L,
                Collections.singletonList(new VehicleTypesDTO(true, "Passenger Car")));
        Manufacturer manufacturer = new Manufacturer(1L, "GERMANY", "BMW", "BMW AG", 966L, null);
        Manufacturer manufacturer2 = new Manufacturer(1L, "GERMANY", "BMW", "BMW M GMBH", 967L,
                Collections.singletonList(new VehicleTypes(true, "Passenger Car")));

        List<ManufacturerResponse> manufacturerDTOs = Arrays.asList(manufacturerDTO, manufacturerDTO2);
        List<Manufacturer> manufacturers = Arrays.asList(manufacturer, manufacturer2);

        Specification<Manufacturer> specs = Specification.where(CommonSpecifications.mfrNameLike("bmw"));

        given(manufacturerRepository.findAll(specs)).willReturn(manufacturers);

        assertThat(manufacturerService.searchManufacturers("bmw")).isEqualTo(manufacturerDTOs);
    }

    @Test
    public void createManufacturers_ReturnManufacturersLoadedMessage() {
        ManufacturerDTO manufacturerDTO1 = new ManufacturerDTO("Japan", "toyota", "Toyota Motor Corporation", 1057L, new ArrayList<>());
        ManufacturerDTO manufacturerDTO2 = new ManufacturerDTO("United States (USA)", "Ford", "Ford Motors USA", 1095L, Collections.singletonList(new VehicleTypesDTO(false, "Multipurpose Passenger Vehicle (MPV)")));
        Manufacturer manufacturer1 = new Manufacturer(1L, "Japan", "toyota", "Toyota Motor Corporation", 1057L, new ArrayList<>());
        Manufacturer manufacturer2 = new Manufacturer(2L, "Japan", "toyota", "Toyota Motor Corporation", 1057L, new ArrayList<>());

        List<ManufacturerDTO> manufacturers = List.of(manufacturerDTO1, manufacturerDTO2);

        given(manufacturerRepository.save(any(Manufacturer.class))).willReturn(manufacturer1);


        String response = manufacturerService.create(manufacturers);
        assertThat(response).isEqualTo("Successfully Loaded Manufacturers");
    }

    @Test
    public void createManufacturers_InvalidConstraint() {
        Manufacturer manufacturer = new Manufacturer(1L, "Japan", "toyota", "Toyota Motor Corporation", 1057L, new ArrayList<>());
        ManufacturerDTO createdManufactureDTO = new ManufacturerDTO("Japan", "toyota09543_??-\\//", "Toyota Motor Corporation", 1057L, new ArrayList<>());

        given(manufacturerRepository.save(any(Manufacturer.class))).willThrow(new InvalidConstraintException());
        assertThrows(InvalidConstraintException.class, () -> manufacturerService.create(Collections.singletonList(createdManufactureDTO)));
    }
}
