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
