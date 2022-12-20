package com.airscholar.PaymentService.command.api.aggregate;

import com.airscholar.CommonService.commands.ValidatePaymentCommand;
import com.airscholar.CommonService.events.PaymentProcessedEvent;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.axonframework.commandhandling.CommandHandler;
import org.axonframework.eventsourcing.EventSourcingHandler;
import org.axonframework.modelling.command.AggregateIdentifier;
import org.axonframework.spring.stereotype.Aggregate;

import static org.axonframework.modelling.command.AggregateLifecycle.apply;

@Aggregate
@Slf4j
public class PaymentAggregate {
    @AggregateIdentifier
    private String paymentId;
    private String orderId;

    public PaymentAggregate() {
    }
    @CommandHandler
    public PaymentAggregate(ValidatePaymentCommand validatePaymentCommand){
        //validate the payment details
        //public the payment processed events
        log.info("Executing ValidatePaymentCommand for orderId: {} and Payment Id: {}",
                validatePaymentCommand.getOrderId(), validatePaymentCommand.getPaymentId());

        PaymentProcessedEvent paymentProcessedEvent = PaymentProcessedEvent.builder()
                .orderId(validatePaymentCommand.getOrderId())
                .paymentId(validatePaymentCommand.getPaymentId())
                .build();

        apply(paymentProcessedEvent);

        log.info("PaymentProcessedEvent published for orderId: {} and Payment Id: {}",
                validatePaymentCommand.getOrderId(), validatePaymentCommand.getPaymentId());
    }

    @EventSourcingHandler
    public void on(PaymentProcessedEvent paymentProcessedEvent){
        log.info("PaymentProcessedEvent received for orderId: {} and Payment Id: {}",
                paymentProcessedEvent.getOrderId(), paymentProcessedEvent.getPaymentId());
        this.paymentId = paymentProcessedEvent.getPaymentId();
        this.orderId = paymentProcessedEvent.getOrderId();
    }
}
