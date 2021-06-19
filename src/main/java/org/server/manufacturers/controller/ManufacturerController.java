package org.server.manufacturers.controller;

import org.server.manufacturers.dto.ManufacturerDTO;
import org.server.manufacturers.dto.UpdateManufactureDTORequest;
import org.server.manufacturers.service.ManufacturerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/manufacturers")
public class ManufacturerController {

    @Autowired
    private ManufacturerService manufacturerService;

    @GetMapping("{id}")
    public ManufacturerDTO findManufacturer(@PathVariable Long id) {
        return manufacturerService.findById(id);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public String create(@Valid @RequestBody List<ManufacturerDTO> manufacturerDTOS) {
        return manufacturerService.create(manufacturerDTOS);
    }

    @PutMapping("{id}")
    public String updateManufacturer(@PathVariable Long id, @Valid @RequestBody UpdateManufactureDTORequest updatedManufactureDTORequest) {
        return manufacturerService.updateManufacturer(id, updatedManufactureDTORequest);
    }

    @DeleteMapping("{id}")
    public void deleteManufacturer(@PathVariable Long id) {
        manufacturerService.deleteManufacturer(id);
    }

    @GetMapping
    public List<ManufacturerDTO> findAllManufacturers() {
        return manufacturerService.findAllManufacturers();
    }

}
