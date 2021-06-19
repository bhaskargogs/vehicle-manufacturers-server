package org.server.manufacturers.entity;

import lombok.Getter;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import java.io.Serializable;

@Embeddable
public class VehicleTypes implements Serializable {

    @Getter
    @Column(name = "is_primary")
    private boolean primary;

    @Getter
    @Column(name = "vehicle_type_name")
    private String name;

    private VehicleTypes(){}

    public VehicleTypes(boolean primary, String name) {
        this.primary = primary;
        this.name = name;
    }
}
