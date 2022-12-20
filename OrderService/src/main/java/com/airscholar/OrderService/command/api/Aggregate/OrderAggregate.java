package com.airscholar.OrderService.command.api.Aggregate;

import com.airscholar.CommonService.commands.CompleteOrderCommand;
import com.airscholar.CommonService.events.OrderCompletedEvent;
import com.airscholar.CommonService.events.OrderShippedEvent;
import com.airscholar.OrderService.command.api.command.CreateOrderCommand;
import com.airscholar.OrderService.command.api.data.Order;
import com.airscholar.OrderService.command.api.events.OrderCreatedEvent;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.axonframework.commandhandling.CommandHandler;
import org.axonframework.eventsourcing.EventSourcingHandler;
import org.axonframework.modelling.command.AggregateIdentifier;
import org.axonframework.spring.stereotype.Aggregate;
import org.springframework.beans.BeanUtils;

import static org.axonframework.modelling.command.AggregateLifecycle.apply;

@Aggregate
@Slf4j
public class OrderAggregate {

    @AggregateIdentifier
    private String orderId;
    private String productId;
    private String userId;
    private String addressId;
    private Integer quantity;
    private String orderStatus;

    public OrderAggregate() {

    }
    @CommandHandler
    public OrderAggregate(CreateOrderCommand createOrderCommand){
        //Validation goes here
        log.info("Inside OrderAggregate CreateOrderCommand");
        OrderCreatedEvent orderCreatedEvent = new OrderCreatedEvent();
        log.info("user id => {}", createOrderCommand.getUserId());
        BeanUtils.copyProperties(createOrderCommand, orderCreatedEvent);
        apply(orderCreatedEvent);
    }

    @EventSourcingHandler
    public void on(OrderCreatedEvent orderCreatedEvent){
        this.orderId = orderCreatedEvent.getOrderId();
        this.productId = orderCreatedEvent.getProductId();
        this.userId = orderCreatedEvent.getUserId();
        this.addressId = orderCreatedEvent.getAddressId();
        this.quantity = orderCreatedEvent.getQuantity();
        this.orderStatus = orderCreatedEvent.getOrderStatus();
    }

    @CommandHandler
    public void handle(CompleteOrderCommand completeOrderCommand){
        //validate the command
        //publish order completed event
        OrderCompletedEvent orderCompletedEvent = OrderCompletedEvent.builder()
                .orderStatus(completeOrderCommand.getOrderStatus())
                .orderId(completeOrderCommand.getOrderId())
                .build();

        apply(orderCompletedEvent);
    }

    @EventSourcingHandler
    public void on(OrderCompletedEvent event){
        this.orderStatus= event.getOrderStatus();
    }
}
