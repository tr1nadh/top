package com.example.top.entity.order;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Embeddable;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToOne;
import lombok.*;

import java.time.LocalDate;

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
    private LocalDate bookingDate;
    @OneToOne(
            cascade = {CascadeType.PERSIST, CascadeType.MERGE}
    )
    @JoinColumn(name = "dimensions_id")
    private Dimensions dimensions;

    private int quantity;

    private int printingCharges;
    private String serviceStatus = "PENDING";

    public void setBookingDate(LocalDate bookingDate) {
        if (bookingDate == null) return;

        this.bookingDate = bookingDate;
    }
}
