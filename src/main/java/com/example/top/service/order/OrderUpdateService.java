package com.example.top.service.order;

import com.example.top.dto.ResponseDto;
import com.example.top.entity.order.Order;
import com.example.top.enums.OrderStatus;
import com.example.top.enums.PaymentStatus;
import com.example.top.enums.ServiceStatus;
import com.example.top.repository.OrderRepository;
import lombok.extern.java.Log;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Service;

@Service
@Log
public class OrderUpdateService {

    @Autowired
    private OrderRepository repository;

    public ResponseDto addAmount(Long orderId, int addAmount) {
        if (orderId == null) throw new IllegalArgumentException("'orderId' cannot be null");

        var optDbOrder = repository.findById(orderId);
        if (optDbOrder.isEmpty())
            throw new IllegalStateException("Cannot update amount of order: No order exists with the id '" + orderId + "'");

        var dbOrder = optDbOrder.get();
        var prevAm = dbOrder.getPayment().getAmountPaid();
        var addedAmount = prevAm + addAmount;
        if ((addedAmount) > dbOrder.getPayment().getTotalAmount())
            throw new IllegalStateException("Cannot add more then required amount");

        dbOrder.getPayment().setAmountPaid(addedAmount);
        updatePaymentStatus(dbOrder);
        repository.save(dbOrder);

        var message = "Amount '" + addAmount + "' has been added to the order '" + orderId + "'";
        log.info(message);

        return ResponseDto.builder().success(true).message(message).build();
    }

    public ResponseDto removeAmount(Long orderId, int removeAmount) {
        if (orderId == null) throw new IllegalArgumentException("'orderId' cannot be null");

        var optDbOrder = repository.findById(orderId);
        if (optDbOrder.isEmpty())
            throw new IllegalStateException("Cannot update amount of order: No order exists with the id '" + orderId + "'");

        var dbOrder = optDbOrder.get();
        var prevAm = dbOrder.getPayment().getAmountPaid();
        var removedAmount = prevAm - removeAmount;
        if (removedAmount < 0)
            throw new IllegalStateException("Cannot remove from 0");

        dbOrder.getPayment().setAmountPaid(removedAmount);
        updatePaymentStatus(dbOrder);
        repository.save(dbOrder);

        var message = "Amount '" + removeAmount + "' has been removed from the order '" + orderId + "'";
        log.info(message);

        return ResponseDto.builder().success(true).message(message).build();
    }

    @PreAuthorize("hasAnyRole('EMPLOYEE', 'DEVELOPER')")
    public ResponseDto changeServiceStatus(Long orderId, String serviceStatus) {
        if (orderId == null) throw new IllegalArgumentException("'orderId' cannot be null");

        var optDbOrder = repository.findById(orderId);
        if (optDbOrder.isEmpty())
            throw new IllegalStateException("Cannot update status of order: No order exists with the id '" + orderId + "'");

        var dbOrder = optDbOrder.get();
        if (serviceStatus != null) {
            dbOrder.getService().setServiceStatus(serviceStatus);
            updateOrderStatus(dbOrder);
        }
        repository.save(dbOrder);

        var message = "Order '" + orderId + "' service status changed to '" + serviceStatus + "'";
        log.info(message);

        return ResponseDto.builder().success(true).message(message).build();
    }

    public ResponseDto changePaymentStatus(Long orderId, String paymentStatus) {
        if (orderId == null) throw new IllegalArgumentException("'orderId' cannot be null");

        var optDbOrder = repository.findById(orderId);
        if (optDbOrder.isEmpty())
            throw new IllegalStateException("Cannot update status of order: No order exists with the id '" + orderId + "'");

        var dbOrder = optDbOrder.get();
        if (paymentStatus != null) {
            var payment = dbOrder.getPayment();
            if (paymentStatus.equals(PaymentStatus.PAID.toString())) payment.setAmountPaid(payment.getTotalAmount());
            else payment.setAmountPaid(0);
        }
        updatePaymentStatus(dbOrder);
        repository.save(dbOrder);

        var message = "Order '" + orderId + "' payment status changed to '" + paymentStatus + "'";
        log.info(message);

        return ResponseDto.builder().success(true).message(message).build();
    }

    private void updatePaymentStatus(Order dbOrder) {
        if (dbOrder.getPayment().getAmountPaid() == dbOrder.getPayment().getTotalAmount()) {
            dbOrder.getPayment().setPaymentStatus(PaymentStatus.PAID.toString());
            updateOrderStatus(dbOrder);
        } else if (!dbOrder.getPayment().getPaymentStatus().equals(PaymentStatus.UNPAID.toString())) {
            dbOrder.getPayment().setPaymentStatus(PaymentStatus.UNPAID.toString());
            updateOrderStatus(dbOrder);
        }
    }

    private void updateOrderStatus(Order dbOrder) {
        var serviceStatus = dbOrder.getService().getServiceStatus();
        var paymentStatus = dbOrder.getPayment().getPaymentStatus();

        if (serviceStatus.equals(ServiceStatus.COMPLETED.toString()) && paymentStatus.equals(PaymentStatus.PAID.toString())) {
            dbOrder.setOrderStatus(OrderStatus.COMPLETED.toString());
        } else if (!dbOrder.getOrderStatus().equals(OrderStatus.PENDING.toString())) {
            dbOrder.setOrderStatus(OrderStatus.PENDING.toString());
        }
    }
}
