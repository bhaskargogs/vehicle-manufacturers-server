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

package org.server.manufacturers.util;

import lombok.extern.slf4j.Slf4j;
import org.server.manufacturers.entity.Manufacturer;
import org.server.manufacturers.entity.Manufacturer_;
import org.server.manufacturers.entity.VehicleTypes;
import org.server.manufacturers.entity.VehicleTypes_;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Component;

import javax.persistence.criteria.Join;
import java.util.List;

@Component
@Slf4j
public class CommonSpecifications {

    public Specification<Manufacturer> idEqual(Long id) {
        return (root, query, criteriaBuilder) ->
                criteriaBuilder.equal(root.get(Manufacturer_.ID), id);
    }

    public Specification<Manufacturer> mfrNameLike(String mfrName) {
        return (root, query, criteriaBuilder) ->
                criteriaBuilder.like(criteriaBuilder.lower(root.get(Manufacturer_.MFR_NAME)), "%" + mfrName + "%");
    }

    public Specification<Manufacturer> mfrCountryLike(String mfrCountry) {
        return (root, query, criteriaBuilder) ->
                criteriaBuilder.like(criteriaBuilder.lower(root.get(Manufacturer_.MFR_COUNTRY)), "%" + mfrCountry + "%");
    }

    public Specification<Manufacturer> mfrCommonNameLike(String mfrCommonName) {
        return (root, query, criteriaBuilder) ->
                criteriaBuilder.like(criteriaBuilder.lower(root.get(Manufacturer_.MFR_COMMON_NAME)), "%" + mfrCommonName + "%");
    }

    public Specification<Manufacturer> mfrIdEqual(Long mfrId) {
        return (root, query, criteriaBuilder) ->
                criteriaBuilder.equal(root.get(Manufacturer_.MFR_ID), mfrId);
    }

    public Specification<Manufacturer> isVehicleTypePrimary(boolean isPrimary) {
        return (root, query, criteriaBuilder) -> {
            Join<Manufacturer, List<VehicleTypes>> vehicleTypesJoin = root.join("vehicleTypes");
            return (vehicleTypesJoin == null) ? criteriaBuilder.conjunction() : criteriaBuilder.equal(vehicleTypesJoin.get("isPrimary"), isPrimary);
        };
    }

    public Specification<Manufacturer> hasVehicleTypeName(String name) {
        return (root, query, criteriaBuilder) -> {
            Join<Manufacturer, List<VehicleTypes>> vehicleTypesJoin = root.join("vehicleTypes");
            return (vehicleTypesJoin == null) ? criteriaBuilder.conjunction() : criteriaBuilder.like(criteriaBuilder.lower(vehicleTypesJoin.get("name")), "%" + name + "%");
        };
    }

}
