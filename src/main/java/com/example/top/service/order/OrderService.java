package com.example.top.service.order;

import com.example.top.entity.order.Order;
import com.example.top.enums.OrderStatus;
import com.example.top.enums.PaymentStatus;
import com.example.top.enums.ServiceStatus;
import com.example.top.util.GeneralUtil;
import lombok.extern.java.Log;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Log
public class OrderService extends OrderCRUDService {

    public List<Order> getOrdersByParams(String search, String orderStatus) {
        if (!GeneralUtil.isQualifiedString(search)) throw new IllegalArgumentException("'search' not a qualified string");
        if (!GeneralUtil.isQualifiedString(orderStatus)) throw new IllegalArgumentException("'orderStatus' not a qualified string");

        List<Order> orders;
        if (GeneralUtil.isQualifiedString(orderStatus)) orders = getOrdersBySearchAndOrderStatus(search, orderStatus);
        else if (!GeneralUtil.isQualifiedString(search)) orders = findOrdersByOrderStatus("Pending");
        else orders = findOrdersByCustomerNameContaining(search);

        return orders;
    }

    private List<Order> getOrdersBySearchAndOrderStatus(String search, String orderStatus) {
        List<Order> orders;
        if (!GeneralUtil.isQualifiedString(search)) orders = findOrdersByOrderStatus(orderStatus);
        else orders = findOrdersByOrderStatusAndCustomerNameContaining(orderStatus, search);

        return orders;
    }

    public void updateAmount(Long orderId, int addAmount, int removeAmount) {
        if (orderId == null) throw new IllegalArgumentException("orderId cannot be null");

        var dbOrder = getOrder(orderId);
        if (dbOrder == null) throw new IllegalStateException("Cannot update amount of order which doesn't exists");

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
        saveOrder(dbOrder);
    }

    public void updateServiceStatus(Long orderId, String serviceStatus) {
        if (orderId == null) throw new IllegalArgumentException("orderId cannot be null");

        var dbOrder = getOrder(orderId);
        if (dbOrder == null) throw new IllegalStateException("Cannot update status of order which doesn't exists");

        if (serviceStatus != null) {
            dbOrder.getService().setServiceStatus(serviceStatus);
            updateOrderStatus(dbOrder);
        }
        saveOrder(dbOrder);
    }

    private void updateAmountStatus(Order dbOrder) {
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

    public void cancelOrder(Long id) {
        if (id == null) throw new IllegalArgumentException("orderId cannot be null");

        var dbOrder = getOrder(id);
        if (dbOrder == null) throw new IllegalStateException("Cannot cancel the order which doesn't exists");

        dbOrder.setOrderStatus(OrderStatus.CANCELLED.toString());
        dbOrder.getPayment().setAmountPaid(0);
        saveOrder(dbOrder);

        log.info("Order with the id '" + id + "' has been cancelled");
    }

}
