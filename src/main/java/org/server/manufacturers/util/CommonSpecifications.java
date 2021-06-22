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

import org.server.manufacturers.entity.Manufacturer;
import org.server.manufacturers.entity.Manufacturer_;
import org.server.manufacturers.entity.VehicleTypes;
import org.server.manufacturers.entity.VehicleTypes_;
import org.springframework.data.jpa.domain.Specification;

import javax.persistence.criteria.Join;
import java.util.List;

public class CommonSpecifications {

    public static Specification<Manufacturer> mfrNameLike(String mfrName) {
        return (root, query, criteriaBuilder) ->
                criteriaBuilder.like(criteriaBuilder.lower(root.get(Manufacturer_.MFR_NAME)), "%" + mfrName + "%");
    }

    public static Specification<Manufacturer> mfrCountryLike(String mfrCountry) {
        return (root, query, criteriaBuilder) ->
                criteriaBuilder.like(criteriaBuilder.lower(root.get(Manufacturer_.MFR_COUNTRY)), "%" + mfrCountry + "%");
    }

    public static Specification<Manufacturer> mfrCommonNameLike(String mfrCommonName) {
        return (root, query, criteriaBuilder) ->
                criteriaBuilder.like(criteriaBuilder.lower(root.get(Manufacturer_.MFR_COMMON_NAME)), "%" + mfrCommonName + "%");
    }

    public static Specification<Manufacturer> mfrIdLike(String mfrId) {
        return (root, query, criteriaBuilder) ->
                criteriaBuilder.equal(root.get(Manufacturer_.MFR_ID), mfrId);
    }

    public static Specification<Manufacturer> belongsToVehicleTypes(List<VehicleTypes> vehicleTypes) {
        return (root, query, criteriaBuilder) ->
                criteriaBuilder.in(root.get(Manufacturer_.VEHICLE_TYPES)).value(vehicleTypes);
    }

    public static Specification<Manufacturer> isVehicleTypePrimary(boolean isPrimary) {
        return (root, query, criteriaBuilder) -> {
            Join<Manufacturer, List<VehicleTypes>> vehicleTypesJoin = root.join("vehicleTypes");
            return criteriaBuilder.equal(vehicleTypesJoin.get("isPrimary"), isPrimary);
        };
    }

    public static Specification<Manufacturer> hasVehicleTypeName(String name) {
        return (root, query, criteriaBuilder) -> {
            Join<Manufacturer, List<VehicleTypes>> vehicleTypesJoin = root.join("vehicleTypes");
            return criteriaBuilder.equal(criteriaBuilder.lower(vehicleTypesJoin.get("name")), name);
        };
    }

}
