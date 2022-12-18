package com.airscholar.OrderService.command.api.saga;

import com.airscholar.CommonService.commands.ValidatePaymentCommand;
import com.airscholar.CommonService.events.PaymentProcessedEvent;
import com.airscholar.CommonService.model.User;
import com.airscholar.CommonService.queries.GetUserPaymentDetailsQuery;
import com.airscholar.OrderService.command.api.events.OrderCreatedEvent;
import lombok.extern.slf4j.Slf4j;
import org.axonframework.commandhandling.gateway.CommandGateway;
import org.axonframework.messaging.responsetypes.ResponseTypes;
import org.axonframework.modelling.saga.SagaEventHandler;
import org.axonframework.modelling.saga.StartSaga;
import org.axonframework.queryhandling.QueryGateway;
import org.axonframework.spring.stereotype.Saga;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.UUID;

@Saga
@Slf4j
public class OrderProcessingSaga {

    private final CommandGateway commandGateway;
    private final QueryGateway queryGateway;

    @Autowired
    public OrderProcessingSaga(CommandGateway commandGateway, QueryGateway queryGateway) {
        this.commandGateway = commandGateway;
        this.queryGateway = queryGateway;
    }


    @StartSaga
    @SagaEventHandler(associationProperty = "orderId")
    private void handle(OrderCreatedEvent event) {
        log.info("OrderProcessingSaga started for orderId: {}", event.getOrderId());


        GetUserPaymentDetailsQuery getUserPaymentDetailsQuery = new GetUserPaymentDetailsQuery(event.getUserId());

        User user = null;

        try{
            user = queryGateway.query(getUserPaymentDetailsQuery, ResponseTypes.instanceOf(User.class)).join();
        }catch (Exception e){
            log.error("Exception Occurred", e);
            // Start the compensation process
        }

        ValidatePaymentCommand validatePaymentCommand = ValidatePaymentCommand.builder()
                .orderId(event.getOrderId())
                .cardDetails(user.getCardDetails())
                .paymentId(UUID.randomUUID().toString())
                .build();

        commandGateway.sendAndWait(validatePaymentCommand);
    }

    @SagaEventHandler(associationProperty = "orderId")
    private void handle(PaymentProcessedEvent event){
        log.info("PaymentProcessedEvent received for orderId: {}", event.getOrderId());
        ed
    }
}
