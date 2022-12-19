package com.airscholar.ShipmentService.command.api.aggregate;

import com.airscholar.CommonService.commands.ShipOrderCommand;
import com.airscholar.OrderService.command.api.events.OrderShippedEvent;
import lombok.NoArgsConstructor;
import org.axonframework.commandhandling.CommandHandler;
import org.axonframework.eventsourcing.EventSourcingHandler;
import org.axonframework.modelling.command.TargetAggregateIdentifier;
import org.axonframework.spring.stereotype.Aggregate;

import static org.axonframework.modelling.command.AggregateLifecycle.apply;

@NoArgsConstructor
@Aggregate
public class ShipmentAggregate {
    @TargetAggregateIdentifier
    private String shipmentId;
    private String orderId;
    private String shipmentStatus;


    @CommandHandler
    public ShipmentAggregate(ShipOrderCommand shipOrderCommand){
        //validate the command
        //publish the order shipped event
        OrderShippedEvent orderShippedEvent = OrderShippedEvent.builder()
                .shipmentId(shipOrderCommand.getShipmentId())
                .shipmentStatus("COMPLETED")
                .build();

        apply(orderShippedEvent);
    }

    @EventSourcingHandler
    public void on(OrderShippedEvent event){
        this.shipmentId = event.getShipmentId();
        this.orderId = event.getOrderId();
        this.shipmentStatus = event.getShipmentStatus();
    }

}
