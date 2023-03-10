package com.airscholar.CommonService.commands;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CompleteOrderCommand {
    private String orderId;
    private String orderStatus;
}
