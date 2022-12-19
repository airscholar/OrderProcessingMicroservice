package com.airscholar.CommonService.commands;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class CompleteOrderCommand {
    private String orderId;
    private String orderStatus;
}
