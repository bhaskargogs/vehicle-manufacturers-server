package org.server.manufacturers.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ManufacturerListResponse {

    private List<ManufacturerResponse> manufacturersList;
    private Long totalManufacturers;
}
