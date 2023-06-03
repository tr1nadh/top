package com.example.top.service.order;

import com.example.top.entity.order.Order;
import com.example.top.enums.OrderStatus;
import com.example.top.enums.PaymentStatus;
import com.example.top.enums.ServiceStatus;
import com.example.top.repository.OrderRepository;
import com.example.top.service.ServiceHelper;
import lombok.extern.java.Log;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
@Log
public class OrderService extends ServiceHelper {

    @Autowired
    public OrderFindService find;
    @Autowired
    public OrderUpdateService update;
    @Autowired
    private OrderRepository repository;

    public void saveOrder(Order order) {
        if (order == null) throw new IllegalArgumentException("'order' cannot be null");

        prepareOrderForSaving(order);
        repository.save(order);

        log.info("Order with the id '" + order.getOrderId() + "' has been saved" );
    }

    private void prepareOrderForSaving(Order order) {
        if (order.getOrderId() == null) setDefaultValues(order);
        else setUpdateValues(order);
    }

    private void setUpdateValues(Order order) {
        var dbOrder = repository.findById(order.getOrderId()).get();
        var prevBookingDate = dbOrder.getService().getBookingDate();
        order.getService().setBookingDate(prevBookingDate);
        var prevAmountPaid = dbOrder.getPayment().getAmountPaid();
        var amountPaid = order.getPayment().getAmountPaid();
        order.getPayment().setAmountPaid(Math.max(amountPaid, prevAmountPaid));
        setPayableAmount(order);
        setHandleBy(order);
    }

    private void setPayableAmount(Order order) {
        if (order.getService().isAnyChargesChanged()) {
            var totalAmount = order.getService().getPrintingCharges() + order.getService().getServiceCharges();
            order.getPayment().setTotalAmount(totalAmount);
        }
    }

    private void setDefaultValues(Order order) {
        var service = order.getService();
        var totalAmount = service.getPrintingCharges() + service.getServiceCharges();
        order.getPayment().setTotalAmount(totalAmount);
        setHandleBy(order);
    }

    private void setHandleBy(Order order) {
        var empDetails = getCurrentLoggedInUserDetails();
        if (order.getHandleBy() == null) order.setHandleBy(empDetails);
    }

    public Order getOrder(Long id) {
        if (id == null) throw new IllegalArgumentException("'id' cannot be null");

        var optionalOrder = repository.findById(id);
        if (optionalOrder.isEmpty()) {
            log.severe("No order found with the id '" + id + "'");
            return null;
        }

        log.info("Order with the id '" + id + "' has been retrieved");
        return optionalOrder.get();
    }

    public void deleteOrder(Long id) {
        if (id == null) throw new IllegalArgumentException("'id' cannot be null");

        var order = repository.findById(id);
        if (order.isEmpty())
            throw new IllegalStateException("Cannot delete the order: No order exists with the id '" + id + "'");

        repository.deleteById(id);

        log.info("Order with the id '" + id + "' has been deleted");
    }

    public void moveOrderToPending(Long id) {
        if (id == null) throw new IllegalArgumentException("'orderId' cannot be null");

        var optDbOrder = repository.findById(id);
        if (optDbOrder.isEmpty())
            throw new IllegalStateException("Cannot move the order: No order exists with the id '" + id + "'");

        var dbOrder = optDbOrder.get();
        dbOrder.setOrderStatus(OrderStatus.PENDING.toString());
        dbOrder.getService().setServiceStatus(ServiceStatus.PENDING.toString());
        repository.save(dbOrder);

        log.info("Order with the id '" + id + "' has been moved to pending");
    }

    public void cancelOrder(Long id) {
        if (id == null) throw new IllegalArgumentException("'orderId' cannot be null");

        var optDbOrder = repository.findById(id);
        if (optDbOrder.isEmpty())
            throw new IllegalStateException("Cannot cancel the order: No order exists with the id '" + id + "'");

        var dbOrder = optDbOrder.get();
        dbOrder.setOrderStatus(OrderStatus.CANCELLED.toString());
        dbOrder.getPayment().setAmountPaid(0);
        dbOrder.getPayment().setPaymentStatus(PaymentStatus.UNPAID.toString());
        repository.save(dbOrder);

        log.info("Order with the id '" + id + "' has been cancelled");
    }

}
