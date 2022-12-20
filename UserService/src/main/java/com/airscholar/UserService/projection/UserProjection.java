package com.airscholar.UserService.projection;

import com.airscholar.CommonService.queries.GetUserPaymentDetailsQuery;
import com.airscholar.CommonService.data.User;
import com.airscholar.CommonService.data.UserRepository;
import lombok.extern.slf4j.Slf4j;
import org.axonframework.queryhandling.QueryHandler;
import org.springframework.stereotype.Component;

@Component
@Slf4j
public class UserProjection {

    @QueryHandler
    public User getUserPaymentDetails(GetUserPaymentDetailsQuery query){
        log.info("Handling query to get user details for user => {}", query.getUserId());
        return null;
    }

}
