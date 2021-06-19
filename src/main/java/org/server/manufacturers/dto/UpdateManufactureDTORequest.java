package org.server.manufacturers.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import org.server.manufacturers.entity.VehicleTypes;

import javax.validation.constraints.NotNull;
import java.util.List;

@EqualsAndHashCode(callSuper = true)
@Data
@NoArgsConstructor
public class UpdateManufactureDTORequest extends ManufacturerDTO {

    @NotNull(message = "ID cannot be null")
    private Long id;

    public UpdateManufactureDTORequest(Long id, String country, String mfrCommonName, String mfrName, Long mfrID, List<VehicleTypes> vehicleTypes) {
        super(country, mfrCommonName, mfrName, mfrID, vehicleTypes);
        this.id = id;
    }
}
