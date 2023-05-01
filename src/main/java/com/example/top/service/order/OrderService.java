package com.example.top.service.order;

import com.example.top.enums.OrderStatus;
import com.example.top.enums.ServiceStatus;
import com.example.top.repository.OrderRepository;
import lombok.extern.java.Log;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
@Log
public class OrderService {

    @Autowired
    private OrderRepository repository;
    @Autowired
    public OrderParamsService params;
    @Autowired
    public OrderUpdateService update;
    @Autowired
    public OrderCRUDService crud;

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
        repository.save(dbOrder);

        log.info("Order with the id '" + id + "' has been cancelled");
    }

}
