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

package org.server.manufacturers.entity;

import lombok.Getter;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import java.io.Serializable;

@Embeddable
public class VehicleTypes implements Serializable {

    @Getter
    @Column(name = "is_primary")
    private boolean IsPrimary;

    @Getter
    @Column(name = "vehicle_type_name")
    private String name;

    private VehicleTypes(){}

    public VehicleTypes(boolean IsPrimary, String name) {
        this.IsPrimary = IsPrimary;
        this.name = name;
    }
}
