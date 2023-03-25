package com.example.top.entity.order;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Embeddable;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToOne;
import jakarta.validation.constraints.NotEmpty;
import lombok.*;

@Embeddable
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class Service {

    @OneToOne(
            cascade = {CascadeType.PERSIST, CascadeType.MERGE}
    )
    @JoinColumn(name = "service_type_id")
    private ServiceType serviceType;
    @NotEmpty(message = "Date cannot be empty")
    private String bookingDate;
    @OneToOne(
            cascade = {CascadeType.PERSIST, CascadeType.MERGE}
    )
    @JoinColumn(name = "dimensions_id")
    private Dimensions dimensions;
    @NotEmpty(message = "Quantity cannot be empty")
    private int quantity;
    @NotEmpty(message = "Printing charges cannot be empty")
    private int printingCharges;
    private String serviceStatus = "PENDING";
}
