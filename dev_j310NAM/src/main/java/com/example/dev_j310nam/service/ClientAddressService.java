package com.example.dev_j310nam.service;

import com.example.dev_j310nam.dto.AddressDto;
import com.example.dev_j310nam.dto.ClientAddressDto;
import com.example.dev_j310nam.dto.ClientDto;
import com.example.dev_j310nam.dto.TypeEnum;
import java.rmi.NoSuchObjectException;
import java.util.List;
import java.util.Optional;
import java.util.stream.Stream;

public interface ClientAddressService {

    public Stream<ClientAddressDto> findAllClients();

    public Stream<ClientAddressDto> filterClientName(String text);

    public Stream<ClientAddressDto> filterClientType(String text);

    public Stream<ClientAddressDto> filterClientNameType(String text1, String text2);

    public Stream<ClientAddressDto> filterAddressOfClient(String text);

    public List<ClientAddressDto> filterClientTypeNameAddress(String clientType, String clientNameAddress);

    public void saveClient(ClientAddressDto client);
    public void removeClientById(Integer id) throws NoSuchObjectException;
    public Optional<ClientDto> findByIdClient(Integer id)throws NoSuchObjectException;

    public Optional<AddressDto> findByIdAddress(Integer id) throws NoSuchObjectException;

    public void saveAddress(ClientAddressDto client) throws NoSuchObjectException;

    public void removeAddressById(Integer id) throws NoSuchObjectException;

    public Stream <ClientAddressDto> convertClientToClientAddressDto(ClientDto clientDto);

    public void updateClient(ClientAddressDto clientAddressDto) throws NoSuchObjectException;

    public void updateAddress(ClientAddressDto clientAddressDto) throws NoSuchObjectException;

    public static ClientAddressDto convertClientAddressDto(ClientDto clientDto, AddressDto addressDto){
        return  ClientAddressDto.builder()
                    .clientId(clientDto.getClientId())
                    .clientName(clientDto.getClientName())
                    .type(clientDto.getType().getTypeValue())
                    .dateAdded(clientDto.getDateAdded())
                    .addressId(addressDto!=null ? addressDto.getAddressId() : null)
                    .ip(addressDto!=null ? addressDto.getIp() : null)
                    .mac(addressDto!=null ? addressDto.getMac() : null)
                    .model(addressDto!=null ? addressDto.getModel() : null)
                    .address(addressDto!=null ? addressDto.getAddress() : null)
                    .build();
    }

    public static ClientAddressDto clientEmptyAddress(ClientDto client){
        return ClientAddressDto.builder()
                .clientId(client.getClientId())
                .clientName(client.getClientName())
                .type(client.getType().getTypeValue())
                .dateAdded(client.getDateAdded())
                .addressId(0)
                .ip("")
                .mac("")
                .model("")
                .address("")
                .build();

    }

    public static ClientDto convertClientAddressDtoToClientDto(ClientAddressDto client){
        return ClientDto.builder()
                .clientId(client.getClientId())
                .clientName(client.getClientName())
                .type(TypeEnum.getTypeEnum(String.valueOf(client.getType())))
                .dateAdded(client.getDateAdded())
                .build();
    }

}
