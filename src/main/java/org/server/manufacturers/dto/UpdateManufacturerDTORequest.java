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

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;
import java.util.List;

@EqualsAndHashCode(callSuper = true)
@Data
@NoArgsConstructor
public class UpdateManufacturerDTORequest extends ManufacturerDTO {

    @NotNull(message = "ID cannot be null")
    private Long id;

    public UpdateManufacturerDTORequest(Long id, String country, String mfrCommonName, String mfrName, Long mfrID, List<VehicleTypesDTO> vehicleTypes) {
        super(country, mfrCommonName, mfrName, mfrID, vehicleTypes);
        this.id = id;
    }
}
