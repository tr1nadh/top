package com.example.top.entity.order;

import jakarta.persistence.*;
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

    @Temporal(TemporalType.DATE)
    private LocalDate bookingDate = LocalDate.now();
    @OneToOne(
            cascade = {CascadeType.PERSIST, CascadeType.MERGE}
    )
    @JoinColumn(name = "dimensions_id")
    private Dimensions dimensions;

    private int quantity;

    private int printingCharges;
    private int serviceCharges;
    @Transient
    private boolean anyChargesChanged;
    private String serviceStatus = "PENDING";

    public void setBookingDate(LocalDate bookingDate) {
        if (bookingDate == null) return;

        this.bookingDate = bookingDate;
    }

    public void setPrintingCharges(int printingCharges) {
        anyChargesChanged = true;
        this.printingCharges = printingCharges;
    }

    public void setServiceCharges(int serviceCharges) {
        anyChargesChanged = true;
        this.serviceCharges = serviceCharges;
    }
}
