package com.example.top.entity.order;

import com.example.top.entity.ServiceType;
import jakarta.persistence.Embeddable;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToOne;
import lombok.*;

@Embeddable
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class Service {

    @OneToOne
    @JoinColumn(name = "service_type_id")
    private ServiceType serviceType;
    private String bookingDate;
    @OneToOne
    @JoinColumn(name = "dimensions_id")
    private Dimensions dimensions;
    private int noOfSheets;
    private int printingCharges;
}
