package com.nttdata.bc.models;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Client {
    private Integer clientId;
    private String documentIdentityType;
    private String documentIdentity;
    private String name;
    private String email;
    private String phone;
}
