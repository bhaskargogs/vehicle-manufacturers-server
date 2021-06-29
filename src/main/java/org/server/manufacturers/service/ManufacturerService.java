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
import org.server.manufacturers.dto.ManufacturerListResponse;
import org.server.manufacturers.dto.ManufacturerResponse;
import org.server.manufacturers.dto.UpdateManufacturerDTORequest;
import org.server.manufacturers.entity.Manufacturer;
import org.server.manufacturers.entity.VehicleTypes;
import org.server.manufacturers.exception.InvalidConstraintException;
import org.server.manufacturers.exception.NotFoundException;
import org.server.manufacturers.repository.ManufacturerRepository;
import org.server.manufacturers.util.CommonSpecifications;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.time.OffsetDateTime;
import java.util.Collections;
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
            /*
             * Iterating over List of Manufacturer DTOs and saving
             * each Manufacturer with new Created and Updated Date
             * */
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

            /*
             * Updating each updated Manufacturer DTO if manufacturer exists
             * and setting new updated date in DB
             * */
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
        return manufacturers.isEmpty() ? Collections.emptyList() :
                manufacturers.stream()
                        .map(ManufacturerResponse::mapManufacturerToResponse)
                        .collect(Collectors.toList());
    }

    @Transactional(propagation = Propagation.REQUIRED)
    public ManufacturerListResponse findAllManufacturers(int pageNo, int pageSize, String direction, String fieldName) {
        if (!direction.equalsIgnoreCase("asc") && !direction.equalsIgnoreCase("desc")) {
            throw new InvalidConstraintException();
        }
        Pageable pageable = PageRequest.of(pageNo, pageSize, (direction.equals("asc")) ? Sort.by(fieldName).ascending() : Sort.by(fieldName).descending());
        Page<Manufacturer> manufacturers = manufacturerRepository.findAll(pageable);
        ManufacturerListResponse manufacturerListResponse = new ManufacturerListResponse();
        if (manufacturers.hasContent()) {
            manufacturerListResponse.setManufacturersList(manufacturers.getContent().stream()
                    .map(ManufacturerResponse::mapManufacturerToResponse)
                    .collect(Collectors.toList()));
            manufacturerListResponse.setTotalManufacturers(manufacturers.getTotalElements());
        } else {
            manufacturerListResponse.setManufacturersList(Collections.emptyList());
            manufacturerListResponse.setTotalManufacturers((long) Collections.emptyList().size());
        }
        return manufacturerListResponse;
    }

    private static Optional<Manufacturer> findManufacturerById(ManufacturerRepository repository, Long id) {
        return repository.findById(id);
    }

    @Transactional(propagation = Propagation.REQUIRED)
    public List<ManufacturerResponse> searchManufacturers(String searchParam) {
        Specification<Manufacturer> specs;

        // Condition to check whether List of Vehicle types exists
        boolean vehicleTypesExists = manufacturerRepository.findAll(Specification.where(commonSpecifications.vehicleTypeExists())).isEmpty();

        /*
         * Searching based on data types:
         * 1. Check whether the search param is boolean and inside vehicle types
         * 2. Check whether the search param is Long
         * 3. Check whether the list of vehicle types is empty
         * 4. Check within the list of vehicle types
         */
        if (searchParam.equalsIgnoreCase("true") || searchParam.equalsIgnoreCase("false")) {
            specs = Specification.where(commonSpecifications.isVehicleTypePrimary(Boolean.parseBoolean(searchParam)));
        } else if (searchParam.matches("\\d+")) {
            specs = Specification.where(commonSpecifications.mfrIdEqual(Long.parseLong(searchParam)));
        } else if (vehicleTypesExists) {
            specs = Specification.where(commonSpecifications.mfrCommonNameLike(searchParam.toLowerCase())
                    .or(commonSpecifications.mfrCommonNameLike(searchParam.toLowerCase()))
                    .or(commonSpecifications.mfrNameLike(searchParam.toLowerCase())));
        } else {
            specs = Specification.where(commonSpecifications.hasVehicleTypeName(searchParam.toLowerCase()));
        }

        List<Manufacturer> manufacturers = manufacturerRepository.findAll(specs)
                .stream().distinct().collect(Collectors.toList());

        return manufacturers.isEmpty() ? Collections.emptyList() :
                manufacturers.stream()
                        .map(ManufacturerResponse::mapManufacturerToResponse)
                        .collect(Collectors.toList());
    }
}
