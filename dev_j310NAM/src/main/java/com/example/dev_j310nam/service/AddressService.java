package com.example.dev_j310nam.service;

import com.example.dev_j310nam.dto.AddressDto;
import com.example.dev_j310nam.entity.AddressEntity;
import com.example.dev_j310nam.common_interface.ServiceEntityCrud;

import java.util.stream.Stream;


public interface AddressService extends ServiceEntityCrud<AddressDto,Integer> {

    public Stream<AddressDto> findAddressesDtoByClientId(Integer id);

    public Stream<AddressDto> filterAddressOfClient(String text);

    static AddressDto convertEntityToDtoForAddress(AddressEntity addressEntity){
        return AddressDto.builder()
                .addressId(addressEntity.getAddressId())
                .ip(addressEntity.getIp())
                .mac(addressEntity.getMac())
                .model(addressEntity.getModel())
                .address(addressEntity.getAddress())
                .clientDto(ClientService.convertEntityToDtoForClient(addressEntity.getClient()))
                .build();
    }

    static AddressEntity convertDtoToEntityForAddress(AddressDto addressDto){
        AddressEntity addressEntity = new AddressEntity();
        addressEntity.setAddressId(addressDto.getAddressId());
        addressEntity.setIp(addressDto.getIp());
        addressEntity.setMac(addressDto.getMac());
        addressEntity.setModel(addressDto.getModel());
        addressEntity.setAddress(addressDto.getAddress());
        addressEntity.setClient(ClientService.convertDtoToEntityForClient(addressDto.getClientDto()));
        return addressEntity;
    }

}
