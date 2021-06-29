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

import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ManufacturerResponse {
    private Long id;
    private String country;
    private String mfrCommonName;
    private String mfrName;
    private Long mfrId;
    private List<VehicleTypesDTO> vehicleTypes;

    public static ManufacturerResponse mapManufacturerToResponse(Manufacturer manufacturer) {
        ManufacturerResponse manufacturerResponse = new ManufacturerResponse();
        manufacturerResponse.setId(manufacturer.getId());
        manufacturerResponse.setCountry(manufacturer.getMfrCountry());
        manufacturerResponse.setMfrCommonName(manufacturer.getMfrCommonName());
        manufacturerResponse.setMfrId(manufacturer.getMfrID());
        manufacturerResponse.setMfrName(manufacturer.getMfrName());
        List<VehicleTypesDTO> vehicleTypesDTOS = (manufacturer.getVehicleTypes().isEmpty()) ?
                Collections.emptyList() :
                (manufacturer.getVehicleTypes().stream().map(vehicleType -> {
                    VehicleTypesDTO vehicleTypesDTO = new VehicleTypesDTO();
                    vehicleTypesDTO.setIsPrimary(vehicleType.isPrimary());
                    vehicleTypesDTO.setName(vehicleType.getName());
                    return vehicleTypesDTO;
                }).collect(Collectors.toList()));
        manufacturerResponse.setVehicleTypes(vehicleTypesDTOS);
        return manufacturerResponse;
    }
}
