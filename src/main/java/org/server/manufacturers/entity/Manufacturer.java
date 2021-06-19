package org.server.manufacturers.entity;

import lombok.Getter;
import lombok.Setter;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import java.time.OffsetDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "manufacturers")
public class Manufacturer {

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
