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

package org.server.manufacturers.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.server.manufacturers.entity.Manufacturer;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.util.ArrayList;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ManufacturerDTO {

    @NotBlank(message = "Country cannot be blank")
    private String country;

    @NotBlank(message = "Manufacturer Common Name cannot be blank")
    private String mfrCommonName;

    @NotBlank(message = "Manufacturer Name cannot be null")
    private String mfrName;

    @NotNull(message = "Manufacturer ID cannot be null")
    private Long mfrId;

    private List<VehicleTypesDTO> vehicleTypes;

    public static ManufacturerDTO mapManufacturerToManufacturerDTO(Manufacturer manufacturer) {
        ManufacturerDTO manufacturerDTO = new ManufacturerDTO();
        manufacturerDTO.setCountry(manufacturer.getMfrCountry());
        manufacturerDTO.setMfrCommonName(manufacturer.getMfrCommonName());
        manufacturerDTO.setMfrId(manufacturer.getMfrID());
        manufacturerDTO.setMfrName(manufacturer.getMfrName());
        List<VehicleTypesDTO> vehicleTypesDTOS = new ArrayList<>();
        manufacturer.getVehicleTypes().forEach(vehicleType -> {
            VehicleTypesDTO vehicleTypesDTO = new VehicleTypesDTO();
            vehicleTypesDTO.setIsPrimary(vehicleType.isPrimary());
            vehicleTypesDTO.setName(vehicleType.getName());
            vehicleTypesDTOS.add(vehicleTypesDTO);
        });
        manufacturerDTO.setVehicleTypes(vehicleTypesDTOS);
        return manufacturerDTO;
    }
}
