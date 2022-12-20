package com.airscholar.OrderService.command.api.saga;

import com.airscholar.CommonService.commands.CompleteOrderCommand;
import com.airscholar.CommonService.commands.ValidatePaymentCommand;
import com.airscholar.CommonService.data.CardDetails;
import com.airscholar.CommonService.events.OrderCompletedEvent;
import com.airscholar.CommonService.events.PaymentProcessedEvent;
import com.airscholar.CommonService.queries.GetUserPaymentDetailsQuery;
import com.airscholar.CommonService.commands.ShipOrderCommand;
import com.airscholar.CommonService.service.UserService;
import com.airscholar.OrderService.command.api.events.OrderCreatedEvent;
import com.airscholar.CommonService.events.OrderShippedEvent;
import com.airscholar.CommonService.data.User;
import com.airscholar.CommonService.data.UserRepository;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.axonframework.commandhandling.gateway.CommandGateway;
import org.axonframework.modelling.saga.EndSaga;
import org.axonframework.modelling.saga.SagaEventHandler;
import org.axonframework.modelling.saga.StartSaga;
import org.axonframework.queryhandling.QueryGateway;
import org.axonframework.spring.stereotype.Saga;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.UUID;

@Saga
@Slf4j
@AllArgsConstructor
public class OrderProcessingSaga {

    @Autowired
    private transient CommandGateway commandGateway;
    @Autowired
    private transient QueryGateway queryGateway;

    public OrderProcessingSaga() {

    }

    @StartSaga
    @SagaEventHandler(associationProperty = "orderId")
    public void handle(OrderCreatedEvent event) {
        log.info("OrderProcessingSaga started for orderId: {}", event.getOrderId());

        log.info("USER ID GOES HERE => {}", event.getUserId());

        GetUserPaymentDetailsQuery getUserPaymentDetailsQuery = new GetUserPaymentDetailsQuery(event.getUserId());

        User user = this.getUserDetails(getUserPaymentDetailsQuery.getUserId());

        ValidatePaymentCommand validatePaymentCommand = ValidatePaymentCommand.builder()
                .orderId(event.getOrderId())
                .cardDetails(user.getCardDetails())
                .paymentId(UUID.randomUUID().toString())
                .build();
        log.info("Sending ValidatePaymentCommand to PaymentService");
        commandGateway.sendAndWait(validatePaymentCommand);
    }

    public User getUserDetails(String userId){
//        CardDetails cardDetails = CardDetails.builder()
//                .cardId(1)
//                .cardNumber("123456789")
//                .cvv(123)
//                .name("Yusuf Ganiyu")
//                .validUntilMonth(12)
//                .validUntilYear(2020)
//                .build();

        User user = User.builder()
                .userId(userId)
                .firstName("Yusuf")
                .lastName("Ganiyu")
                .cardDetails("Sample")
                .build();

        return user;
    }

    @SagaEventHandler(associationProperty = "orderId")
    public void handle(PaymentProcessedEvent event) {
        log.info("PaymentProcessedEvent started for orderId: {}", event.getOrderId());
        try {
            ShipOrderCommand shipOrderCommand = ShipOrderCommand.builder()
                    .shipmentId(UUID.randomUUID().toString())
                    .orderId(event.getOrderId())
                    .build();

            commandGateway.sendAndWait(shipOrderCommand);
        } catch (Exception e) {
            log.error(e.getMessage());
            //start the compensating transaction
        }
    }

    @SagaEventHandler(associationProperty = "orderId")
    public void handle(OrderShippedEvent event) {
        log.info("OrderShippedEvent started for orderId: {}", event.getOrderId());

        try {
            CompleteOrderCommand completeOrderCommand = CompleteOrderCommand.builder()
                    .orderId(event.getOrderId())
                    .orderStatus("APPROVED")
                    .build();

            commandGateway.sendAndWait(completeOrderCommand);
        } catch (Exception e) {
            log.error(e.getMessage());
            //start the compensating transaction
        }
    }

    @SagaEventHandler(associationProperty = "orderId")
    @EndSaga
    public void handle(OrderCompletedEvent event) {
        log.info("OrderCompletedEvent started for orderId: {}", event.getOrderId());

    }
}
