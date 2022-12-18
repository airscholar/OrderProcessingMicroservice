package com.airscholar.PaymentService.command.api.events;

import com.airscholar.CommonService.events.PaymentProcessedEvent;
import com.airscholar.PaymentService.command.api.data.Payment;
import com.airscholar.PaymentService.command.api.data.PaymentRepository;
import org.axonframework.eventhandling.EventHandler;
import org.springframework.stereotype.Component;

import java.util.Date;

@Component
public class PaymentsEventHandler {

    private PaymentRepository paymentRepository;

    public PaymentsEventHandler(PaymentRepository paymentRepository) {
        this.paymentRepository = paymentRepository;
    }

    @EventHandler
    public void on(PaymentProcessedEvent event) {
        Payment payment = Payment.builder()
                .paymentId(event.getPaymentId())
                .orderId(event.getOrderId())
                .paymentStatus("COMPLETED")
                .timestamp(new Date())
                .build();

        paymentRepository.save(payment);
    }


}
