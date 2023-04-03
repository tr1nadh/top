package com.example.top.logger;

import com.example.top.entity.order.Order;
import lombok.extern.java.Log;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.stereotype.Component;

@Aspect
@Component
@Log
public class OrderCRUDServiceLogger {

    @AfterReturning(value = "execution(* com.example.top.service.order.OrderCRUDService.saveOrder(..))",
    returning = "result")
    public void saveOrderLog(Object result) {
        if (result == null)
            log.severe("Cannot add null as an order");

        var returnOrder = (Order) result;
        if (returnOrder.getOrderId() == null) log.info("New order has been created" );
        else log.info("Order with the id '" + returnOrder.getOrderId() + "' has been updated" );
    }
}
