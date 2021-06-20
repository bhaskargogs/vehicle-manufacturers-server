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

import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.server.manufacturers.dto.ManufacturerDTO;
import org.server.manufacturers.dto.UpdateManufacturerDTORequest;
import org.server.manufacturers.entity.Manufacturer;
import org.server.manufacturers.entity.VehicleTypes;
import org.server.manufacturers.exception.InvalidConstraintException;
import org.server.manufacturers.exception.NotFoundException;
import org.server.manufacturers.repository.ManufacturerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.OffsetDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Slf4j
@Service
public class ManufacturerService {

    @Autowired
    private ManufacturerRepository manufacturerRepository;

    @Autowired
    private ModelMapper mapper;

    @Transactional
    public ManufacturerDTO findById(Long id) {
        return mapper.map(ManufacturerService.findManufacturerById(manufacturerRepository, id)
                .orElseThrow(NotFoundException::new), ManufacturerDTO.class);
    }

    @Transactional
    public String create(List<ManufacturerDTO> manufacturerDTOS) {
        try {
            manufacturerDTOS.forEach(manufacturerDTO -> {
                Manufacturer newManufacturer = new Manufacturer(manufacturerDTO.getCountry(), manufacturerDTO.getMfrCommonName(),
                        manufacturerDTO.getMfrName(), manufacturerDTO.getMfrId(), manufacturerDTO.getVehicleTypes());
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

    @Transactional
    public String updateManufacturer(Long id, UpdateManufacturerDTORequest updateManufacturerDTORequest) {
        Optional<Manufacturer> foundManufacturer = ManufacturerService.findManufacturerById(manufacturerRepository, id);

        try {
            if (foundManufacturer.isEmpty()) {
                throw new NotFoundException();
            }
            Manufacturer manufacturerToUpdate = new Manufacturer(id, updateManufacturerDTORequest.getCountry(), updateManufacturerDTORequest.getMfrCommonName(),
                    updateManufacturerDTORequest.getMfrName(), updateManufacturerDTORequest.getMfrId(), updateManufacturerDTORequest.getVehicleTypes());
            manufacturerToUpdate.setCreatedDate(foundManufacturer.get().getCreatedDate());
            manufacturerToUpdate.setUpdatedDate(OffsetDateTime.now());
            manufacturerRepository.save(manufacturerToUpdate);
        } catch (InvalidConstraintException ex) {
            log.info("Manufacture Service: updateManufacturer(): " + ex.getMessage());
            throw new InvalidConstraintException();
        }
        return "Successfully Updated";
    }

    @Transactional
    public void deleteManufacturer(Long id) {
        if (manufacturerRepository.findById(id).isEmpty()) {
            throw new NotFoundException();
        }
        manufacturerRepository.deleteById(id);
    }

    @Transactional
    public List<ManufacturerDTO> findAllManufacturers() {
        List<Manufacturer> manufacturers = manufacturerRepository.findAll();
        return manufacturers.isEmpty() ? new ArrayList<>() :
                manufacturers.stream()
                        .map(manufacturer -> mapper.map(manufacturer, ManufacturerDTO.class))
                        .collect(Collectors.toList());
    }

    private static Optional<Manufacturer> findManufacturerById(ManufacturerRepository repository, Long id) {
        return repository.findById(id);
    }
}
