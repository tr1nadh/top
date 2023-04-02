package com.example.top.service.order;

import com.example.top.entity.order.Order;
import com.example.top.enums.OrderStatus;
import com.example.top.enums.PaymentStatus;
import com.example.top.enums.ServiceStatus;
import lombok.extern.java.Log;
import org.springframework.beans.factory.annotation.Autowired;

@Log
public class OrderUpdateService {

    @Autowired
    private OrderService service;

    public void updateAmount(Long orderId, int addAmount, int removeAmount) {
        var dbOrder = service.getOrder(orderId);
        var prevAm = dbOrder.getPayment().getAmountPaid();
        if (addAmount != 0) {
            if ((prevAm + addAmount) > dbOrder.getPayment().getTotalAmount()) {
                log.severe("Cannot add more then enough amount");
                return;
            }
            dbOrder.getPayment().setAmountPaid(prevAm + addAmount);
        }
        else if (removeAmount != 0) {
            if ((prevAm - removeAmount) < 0) {
                log.severe("Cannot remove the amount");
                return;
            }
            dbOrder.getPayment().setAmountPaid(prevAm - removeAmount);
        }

        updateAmountStatus(dbOrder);
        service.saveOrder(dbOrder);
    }

    private static void updateAmountStatus(Order dbOrder) {
        if (dbOrder.getPayment().getAmountPaid() == dbOrder.getPayment().getTotalAmount()) {
            dbOrder.getPayment().setPaymentStatus(PaymentStatus.PAID.toString());
            updateOrderStatus(dbOrder);
        } else if (!dbOrder.getPayment().getPaymentStatus().equals(PaymentStatus.UNPAID.toString())) {
            dbOrder.getPayment().setPaymentStatus(PaymentStatus.UNPAID.toString());
            updateOrderStatus(dbOrder);
        }
    }

    public void updateServiceStatus(Long orderId, String serviceStatus) {
        var dbOrder = service.getOrder(orderId);
        if (serviceStatus != null) {
            dbOrder.getService().setServiceStatus(serviceStatus);
            updateOrderStatus(dbOrder);
        }
        service.saveOrder(dbOrder);
    }

    private static void updateOrderStatus(Order dbOrder) {
        var serviceStatus = dbOrder.getService().getServiceStatus();
        var paymentStatus = dbOrder.getPayment().getPaymentStatus();

        if (serviceStatus.equals(ServiceStatus.COMPLETED.toString()) && paymentStatus.equals(PaymentStatus.PAID.toString())) {
            dbOrder.setOrderStatus(OrderStatus.COMPLETED.toString());
        } else if (!dbOrder.getOrderStatus().equals(OrderStatus.PENDING.toString())) {
            dbOrder.setOrderStatus(OrderStatus.PENDING.toString());
        }
    }
}
