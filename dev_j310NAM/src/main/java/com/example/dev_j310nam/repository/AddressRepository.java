package com.example.dev_j310nam.repository;

import com.example.dev_j310nam.entity.AddressEntity;
import com.example.dev_j310nam.common_interface.ServiceEntityCrud;
import com.example.dev_j310nam.entity.ClientEntity;

import java.util.stream.Stream;

public interface AddressRepository extends ServiceEntityCrud<AddressEntity,Integer> {

    public Stream<AddressEntity> findAddressesEntityByClientId(Integer id);

    public Stream<AddressEntity> filterAddressOfClient(String text);


}
