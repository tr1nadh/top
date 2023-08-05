package com.example.top.service.order;

import com.example.top.dto.ResponseDto;
import com.example.top.entity.order.Order;
import com.example.top.enums.OrderStatus;
import com.example.top.enums.PaymentStatus;
import com.example.top.enums.ServiceStatus;
import com.example.top.repository.OrderRepository;
import com.example.top.security.userdetails.AppSecurity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class OrderCRUDService {

    @Autowired
    private OrderRepository repository;

    @Autowired
    private AppSecurity appSecurity;

    public ResponseDto saveOrder(Order order) {
        if (order == null) throw new IllegalArgumentException("'order' cannot be null");

        prepareOrderForSaving(order);
        var savedOrder = repository.save(order);

        var message = (order.getOrderId() == null) ?  "New order '"+ savedOrder.getOrderId() +"' saved successfully!" :
                "Order '"+ order.getOrderId() +"' has been updated successfully!";
        return ResponseDto.builder().success(true).message(message).build();
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
        var account = appSecurity.getCurrentLoggedInUserDetails();
        if (order.getHandleBy() == null) order.setHandleBy(account.getEmployee());
    }

    public ResponseDto getOrder(Long id) {
        if (id == null) throw new IllegalArgumentException("'id' cannot be null");

        var order = repository.findById(id).orElse(null);

        var message = (order == null) ? "No order found with the id '" + id + "'" :
                "Order '" + id + "' has been retrieved";
        return ResponseDto.builder().success(true).message(message).data(order).build();
    }

    public ResponseDto deleteOrder(Long id) {
        if (id == null) throw new IllegalArgumentException("'id' cannot be null");

        var order = repository.findById(id);
        if (order.isEmpty())
            throw new IllegalStateException("Cannot delete the order: No order exists with the id '" + id + "'");

        repository.deleteById(id);

        var message = "Order '" + id + "' has been deleted";
        return ResponseDto.builder().success(true).message(message).build();
    }

    public ResponseDto moveOrderToPending(Long id) {
        if (id == null) throw new IllegalArgumentException("'orderId' cannot be null");

        var optDbOrder = repository.findById(id);
        if (optDbOrder.isEmpty())
            throw new IllegalStateException("Cannot move the order: No order exists with the id '" + id + "'");

        var dbOrder = optDbOrder.get();
        dbOrder.setOrderStatus(OrderStatus.PENDING.toString());
        dbOrder.getService().setServiceStatus(ServiceStatus.PENDING.toString());
        repository.save(dbOrder);

        var message = "Order '" + id + "' has been moved to pending";
        return ResponseDto.builder().success(true).message(message).build();
    }

    public ResponseDto cancelOrder(Long id) {
        if (id == null) throw new IllegalArgumentException("'orderId' cannot be null");

        var optDbOrder = repository.findById(id);
        if (optDbOrder.isEmpty())
            throw new IllegalStateException("Cannot cancel the order: No order exists with the id '" + id + "'");

        var dbOrder = optDbOrder.get();
        dbOrder.setOrderStatus(OrderStatus.CANCELLED.toString());
        dbOrder.getPayment().setAmountPaid(0);
        dbOrder.getPayment().setPaymentStatus(PaymentStatus.UNPAID.toString());
        repository.save(dbOrder);

        var message = "Order '" + id + "' has been cancelled";
        return ResponseDto.builder().success(true).message(message).build();
    }

}
