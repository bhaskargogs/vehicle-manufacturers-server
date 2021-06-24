package org.server.manufacturers.util;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import org.server.manufacturers.entity.VehicleTypes;

import java.util.List;

@Getter
@Setter
@Builder
public class Filter {
    private String field;
    private QueryOperator operator;
    private String value;
    private List<VehicleTypes> values;
}
