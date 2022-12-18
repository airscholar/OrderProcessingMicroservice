package com.airscholar.PaymentService.command.api.data;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.Id;
import java.util.Date;

@Data
@Builder
@Entity
@NoArgsConstructor
@AllArgsConstructor
public class Payment {
    @Id
    private String paymentId;
    private String orderId;
    private Date timestamp;
    private String paymentStatus;
}
