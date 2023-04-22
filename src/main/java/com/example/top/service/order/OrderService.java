package com.example.top.service.order;

import com.example.top.enums.OrderStatus;
import lombok.extern.java.Log;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
@Log
public class OrderService extends OrderCRUDService {

    @Autowired
    public OrderParamsService params;
    @Autowired
    public OrderUpdateService update;


    public void cancelOrder(Long id) {
        if (id == null) throw new IllegalArgumentException("'orderId' cannot be null");

        var dbOrder = getOrder(id);
        if (dbOrder == null)
            throw new IllegalStateException("Cannot cancel the order: No order exists with the id '" + id + "'");

        dbOrder.setOrderStatus(OrderStatus.CANCELLED.toString());
        dbOrder.getPayment().setAmountPaid(0);
        saveOrder(dbOrder);

        log.info("Order with the id '" + id + "' has been cancelled");
    }

}
