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

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.server.manufacturers.dto.ManufacturerDTO;
import org.server.manufacturers.dto.ManufacturerResponse;
import org.server.manufacturers.dto.UpdateManufacturerDTORequest;
import org.server.manufacturers.entity.Manufacturer;
import org.server.manufacturers.entity.VehicleTypes;
import org.server.manufacturers.exception.InvalidConstraintException;
import org.server.manufacturers.exception.NotFoundException;
import org.server.manufacturers.repository.ManufacturerRepository;
import org.server.manufacturers.util.CommonSpecifications;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.time.OffsetDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Slf4j
@Service
@RequiredArgsConstructor
public class ManufacturerService {

    private final ManufacturerRepository manufacturerRepository;
    private final CommonSpecifications commonSpecifications;

    @Transactional(propagation = Propagation.REQUIRED)
    public ManufacturerResponse findById(Long id) {
        return ManufacturerResponse.mapManufacturerToResponse(
                ManufacturerService.findManufacturerById(manufacturerRepository, id).orElseThrow(NotFoundException::new));
    }

    @Transactional(propagation = Propagation.REQUIRED)
    public String create(List<ManufacturerDTO> manufacturerDTOS) {
        try {
            manufacturerDTOS.forEach(manufacturerDTO -> {
                Manufacturer newManufacturer = new Manufacturer(manufacturerDTO.getCountry(), manufacturerDTO.getMfrCommonName(),
                        manufacturerDTO.getMfrName(), manufacturerDTO.getMfrId(), manufacturerDTO.getVehicleTypes().stream()
                        .map(vehicleTypesDTO -> new VehicleTypes(vehicleTypesDTO.isIsPrimary(), vehicleTypesDTO.getName())).collect(Collectors.toList()));
                newManufacturer.setCreatedDate(OffsetDateTime.now());
                newManufacturer.setUpdatedDate(OffsetDateTime.now());
                manufacturerRepository.save(newManufacturer);
            });
        } catch (InvalidConstraintException ex) {
            log.info("Manufacture Service: createManufacturer(): " + ex.getMessage());
            throw new InvalidConstraintException();
        }
        return manufacturerDTOS.size() > 1 ? "Successfully Loaded Manufacturers" : "Successfully Created";
    }

    @Transactional(propagation = Propagation.REQUIRED)
    public String updateManufacturer(Long id, UpdateManufacturerDTORequest updateManufacturerDTORequest) {
        Optional<Manufacturer> foundManufacturer = ManufacturerService.findManufacturerById(manufacturerRepository, id);

        try {
            if (foundManufacturer.isEmpty()) {
                throw new NotFoundException();
            }
            Manufacturer manufacturerToUpdate = new Manufacturer(id, updateManufacturerDTORequest.getCountry(), updateManufacturerDTORequest.getMfrCommonName(),
                    updateManufacturerDTORequest.getMfrName(), updateManufacturerDTORequest.getMfrId(), updateManufacturerDTORequest.getVehicleTypes().stream()
                    .map(vehicleTypesDTO -> new VehicleTypes(vehicleTypesDTO.isIsPrimary(), vehicleTypesDTO.getName())).collect(Collectors.toList()));
            manufacturerToUpdate.setCreatedDate(foundManufacturer.get().getCreatedDate());
            manufacturerToUpdate.setUpdatedDate(OffsetDateTime.now());
            manufacturerRepository.save(manufacturerToUpdate);
        } catch (InvalidConstraintException ex) {
            log.info("Manufacture Service: updateManufacturer(): " + ex.getMessage());
            throw new InvalidConstraintException();
        }
        return "Successfully Updated";
    }

    @Transactional(propagation = Propagation.REQUIRED)
    public void deleteManufacturer(Long id) {
        if (manufacturerRepository.findById(id).isEmpty()) {
            throw new NotFoundException();
        }
        manufacturerRepository.deleteById(id);
    }

    @Transactional(propagation = Propagation.REQUIRED)
    public List<ManufacturerResponse> findAllManufacturers() {
        List<Manufacturer> manufacturers = manufacturerRepository.findAll();
        return manufacturers.isEmpty() ? new ArrayList<>() :
                manufacturers.stream()
                        .map(ManufacturerResponse::mapManufacturerToResponse)
                        .collect(Collectors.toList());
    }

    private static Optional<Manufacturer> findManufacturerById(ManufacturerRepository repository, Long id) {
        return repository.findById(id);
    }

    @Transactional(propagation = Propagation.REQUIRED)
    public List<ManufacturerResponse> searchManufacturers(String searchParam) {
        Specification<Manufacturer> specs = (searchParam.equalsIgnoreCase("true") || searchParam.equalsIgnoreCase("false")) ?
                Specification.where(commonSpecifications.isVehicleTypePrimary(Boolean.parseBoolean(searchParam))) :
                (searchParam.matches("^[0-9]$")) ?
                        Specification.where(commonSpecifications.mfrIdLike(searchParam)
                                .or(commonSpecifications.idLike(searchParam))) :
                        Specification.where(commonSpecifications.mfrCountryLike(searchParam.toLowerCase()))
                                .or(commonSpecifications.mfrCommonNameLike(searchParam.toLowerCase()))
                                .or(commonSpecifications.mfrNameLike(searchParam.toLowerCase()))
                                .or(commonSpecifications.hasVehicleTypeName(searchParam.toLowerCase()));

        List<Manufacturer> manufacturers = manufacturerRepository.findAll(specs);

        return manufacturers.isEmpty() ? new ArrayList<>() :
                manufacturers.stream()
                        .map(ManufacturerResponse::mapManufacturerToResponse)
                        .collect(Collectors.toList());
    }
}
