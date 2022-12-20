package com.airscholar.UserService.controller;

import com.airscholar.CommonService.queries.GetUserPaymentDetailsQuery;
import com.airscholar.CommonService.data.User;
import lombok.extern.slf4j.Slf4j;
import org.axonframework.queryhandling.QueryGateway;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/users")
@Slf4j
public class UserController {
//    private UserRepository userRepository;
    private final QueryGateway queryGateway;

    public UserController( QueryGateway queryGateway) {
        this.queryGateway = queryGateway;
    }

//    @PostMapping
//    public User saveUserInformation(@RequestBody User user){
//        return this.userRepository.save(user);
//    }
    @GetMapping("{userId}")
    public User getPaymentDetails(@PathVariable String userId){
        GetUserPaymentDetailsQuery query = new GetUserPaymentDetailsQuery(userId);
        log.info("Sending query to get user details for user => {}", query.getUserId());
        return null;//userRepository.findById(query.getUserId()).orElse(null);
    }
}
