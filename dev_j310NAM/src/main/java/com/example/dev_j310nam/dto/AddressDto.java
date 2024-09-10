package com.example.dev_j310nam.dto;

import lombok.*;


@Data
@Builder
@EqualsAndHashCode(exclude = {"model", "address"})
public class AddressDto {

    private Integer addressId;
    private String ip;
    private String mac;
    private String model;
    private String address;
    private ClientDto clientDto;


}
