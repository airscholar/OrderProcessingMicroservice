package com.airscholar.UserService.projection;

import com.airscholar.CommonService.model.CardDetails;
import com.airscholar.CommonService.model.User;
import com.airscholar.CommonService.queries.GetUserPaymentDetailsQuery;
import org.axonframework.queryhandling.QueryHandler;
import org.springframework.stereotype.Component;

@Component
public class UserProjection {

    @QueryHandler
    public User getUserPaymentDetails(GetUserPaymentDetailsQuery query){
        // Ideally get the details from the database
        CardDetails cardDetails = CardDetails.builder()
                .name("Yusuf Ganiyu")
                .cardNumber("1234567890123456")
                .validUntilMonth(12)
                .validUntilYear(2025)
                .cvv(123)
                .build();

        return User.builder()
                .userId(query.getUserId())
                .firstName("Yusuf")
                .lastName("Ganiyu")
                .cardDetails(cardDetails)
                .build();
    }
}
