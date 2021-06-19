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
import org.server.manufacturers.entity.VehicleTypes;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
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
    private Long mfrID;

    private List<VehicleTypes> vehicleTypes;
}
