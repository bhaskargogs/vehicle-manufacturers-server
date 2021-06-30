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

package org.server.manufacturers.cucumber.dto;

import lombok.Data;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

@Data
public class ManufacturerDTO {

    private String country;
    private String mfrCommonName;
    private String mfrName;
    private Long mfrId;
    private List<VehicleType> vehicleTypeList;

    public static ManufacturerDTO createManufacturerDTO(Map<String, String> entry) {
        ManufacturerDTO manufacturerDTO = new ManufacturerDTO();
        manufacturerDTO.setCountry(entry.get("country"));
        manufacturerDTO.setMfrCommonName(entry.get("mfrCommonName"));
        manufacturerDTO.setMfrName(entry.get("mfrName"));
        manufacturerDTO.setMfrId(Long.parseLong(entry.get("mfrId")));
        List<Boolean> isPrimaries = VehicleType.vehicleTypeIsPrimaryList(entry.get("isPrimary"));
        List<String> names = VehicleType.vehicleTypeNameList(entry.get("name"));
        List<VehicleType> vehicleTypes = new ArrayList<>();
        Map<String, Boolean> vehicleTypesMap = IntStream.range(0, isPrimaries.size()).boxed()
                .collect(Collectors.toMap(names::get, isPrimaries::get));

        vehicleTypesMap.forEach((key, value) -> {
            VehicleType vehicleType = new VehicleType(value, key);
            vehicleTypes.add(vehicleType);
        });
        manufacturerDTO.setVehicleTypeList(vehicleTypes);
        return manufacturerDTO;
    }

}
