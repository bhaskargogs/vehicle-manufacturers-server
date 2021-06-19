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
import lombok.Setter;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import java.io.Serializable;
import java.time.OffsetDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "manufacturers")
public class Manufacturer implements Serializable {

    @Getter
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Getter
    @Column(name = "mfr_country")
    private String mfrCountry;

    @Getter
    @Column(name = "mfr_common_name")
    private String mfrCommonName;

    @Getter
    @Column(name = "mfr_id")
    private Long mfrID;

    @Getter
    @Column(name = "mfr_name")
    private String mfrName;

    @Getter
    @Embedded
    @ElementCollection
    @CollectionTable(
            name = "vehicle_types",
            joinColumns = @JoinColumn(name = "manufacturers_id")
    )
    private List<VehicleTypes> vehicleTypes = new ArrayList<>();

    @Setter
    @Getter
    @Column(name = "created_date", nullable = false)
    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME)
    private OffsetDateTime createdDate;

    @Getter
    @Setter
    @Column(name = "updated_date", nullable = false)
    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME)
    private OffsetDateTime updatedDate;

    private Manufacturer() {
    }

    public Manufacturer(String mfrCountry, String mfrCommonName, String mfrName,
                        Long mfrID, List<VehicleTypes> vehicleTypes) {
        this.mfrCountry = mfrCountry;
        this.mfrCommonName = mfrCommonName;
        this.mfrID = mfrID;
        this.mfrName = mfrName;
        this.vehicleTypes = vehicleTypes;
    }

    public Manufacturer(Long id, String mfrCountry, String mfrCommonName,
                        String mfrName, Long mfrID, List<VehicleTypes> vehicleTypes) {
        this(mfrCountry, mfrCommonName, mfrName, mfrID, vehicleTypes);
        this.id = id;
    }
}
