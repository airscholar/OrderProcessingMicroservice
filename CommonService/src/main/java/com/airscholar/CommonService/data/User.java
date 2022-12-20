package com.airscholar.CommonService.data;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Data
@Builder
//@Entity(name = "users")
//@AllArgsConstructor
//@NoArgsConstructor
public class User {
    @Id
    private String userId;
    private String firstName;
    private String lastName;
//    @OneToOne(cascade = CascadeType.ALL)
//    @JoinColumn(name = "card_details_card_id")
    private String cardDetails;
}