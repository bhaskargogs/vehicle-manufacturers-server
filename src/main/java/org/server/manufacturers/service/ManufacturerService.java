package org.server.manufacturers.service;

import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.server.manufacturers.dto.ManufacturerDTO;
import org.server.manufacturers.dto.UpdateManufactureDTORequest;
import org.server.manufacturers.entity.Manufacturer;
import org.server.manufacturers.exception.InvalidConstraintException;
import org.server.manufacturers.exception.NotFoundException;
import org.server.manufacturers.repository.ManufacturerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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

    public ManufacturerDTO findById(Long id) {
        return mapper.map(ManufacturerService.findManufacturerById(manufacturerRepository, id)
                .orElseThrow(NotFoundException::new), ManufacturerDTO.class);
    }

    public String create(List<ManufacturerDTO> manufacturerDTOS) {
        try {
            manufacturerDTOS.forEach(manufacturerDTO -> {
                Manufacturer newManufacturer = new Manufacturer(manufacturerDTO.getCountry(), manufacturerDTO.getMfrCommonName(),
                        manufacturerDTO.getMfrName(), manufacturerDTO.getMfrID(), manufacturerDTO.getVehicleTypes());
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

    public String updateManufacturer(Long id, UpdateManufactureDTORequest updateManufactureDTORequest) {
        Optional<Manufacturer> foundManufacturer = ManufacturerService.findManufacturerById(manufacturerRepository, id);

        try {
            if (foundManufacturer.isEmpty()) {
                throw new NotFoundException();
            }
            Manufacturer manufacturerToUpdate = new Manufacturer(id, updateManufactureDTORequest.getCountry(), updateManufactureDTORequest.getMfrCommonName(),
                    updateManufactureDTORequest.getMfrName(), updateManufactureDTORequest.getMfrID(), updateManufactureDTORequest.getVehicleTypes());
            manufacturerToUpdate.setCreatedDate(foundManufacturer.get().getCreatedDate());
            manufacturerToUpdate.setUpdatedDate(OffsetDateTime.now());
            manufacturerRepository.save(manufacturerToUpdate);
        } catch (InvalidConstraintException ex) {
            log.info("Manufacture Service: updateManufacturer(): " + ex.getMessage());
            throw new InvalidConstraintException();
        }
        return "Successfully Updated";
    }

    public void deleteManufacturer(Long id) {
        if (manufacturerRepository.findById(id).isEmpty()) {
            throw new NotFoundException();
        }
        manufacturerRepository.deleteById(id);
    }

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
