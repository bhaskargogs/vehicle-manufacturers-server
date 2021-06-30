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
import lombok.NoArgsConstructor;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@Data
@NoArgsConstructor
public class VehicleType {
    private boolean isPrimary;
    private String name;

    public VehicleType(boolean isPrimary, String name) {
        this.isPrimary = isPrimary;
        this.name = name;
    }

    public static List<Boolean> vehicleTypeIsPrimaryList(String isPrimaries) {
        return Arrays.stream(isPrimaries.split(",")).map(Boolean::parseBoolean).collect(Collectors.toList());
    }

    public static List<String> vehicleTypeNameList(String names) {
        return Arrays.stream(names.split(",")).collect(Collectors.toList());
    }

}
