package com.airscholar.CommonService.commands;

import com.airscholar.CommonService.data.CardDetails;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.axonframework.modelling.command.TargetAggregateIdentifier;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ValidatePaymentCommand {
    private String paymentId;
    private String orderId;
    private String cardDetails;
}
