package com.example.dev_j310nam.repository;

import com.example.dev_j310nam.entity.AddressEntity;
import com.example.dev_j310nam.entity.ClientEntity;
import com.example.dev_j310nam.common_interface.ServiceEntityCrud;

import java.rmi.NoSuchObjectException;
import java.util.stream.Stream;


public interface ClientRepository extends ServiceEntityCrud<ClientEntity,Integer> {

    public Stream<ClientEntity> filterClientName(String text);

    public Stream<ClientEntity> filterClientType(String text);

    public Stream<ClientEntity> filterClientNameType(String text1, String text2);

}
