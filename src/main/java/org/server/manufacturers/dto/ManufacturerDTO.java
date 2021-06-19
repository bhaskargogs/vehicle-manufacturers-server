package org.server.manufacturers.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.server.manufacturers.entity.VehicleTypes;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ManufacturerDTO implements Serializable {

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
