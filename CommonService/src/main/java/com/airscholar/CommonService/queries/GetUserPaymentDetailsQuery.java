package com.airscholar.CommonService.queries;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
public class GetUserPaymentDetailsQuery {
    private String userId;
}
