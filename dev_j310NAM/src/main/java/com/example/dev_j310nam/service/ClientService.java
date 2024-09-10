package com.example.dev_j310nam.service;

import com.example.dev_j310nam.dto.ClientDto;
import com.example.dev_j310nam.dto.TypeEnum;
import com.example.dev_j310nam.entity.ClientEntity;
import com.example.dev_j310nam.common_interface.ServiceEntityCrud;

import java.util.stream.Stream;

public interface ClientService extends ServiceEntityCrud<ClientDto,Integer> {

    public Stream<ClientDto> filterClientName(String text);

    public Stream<ClientDto> filterClientType(String text);

    public Stream<ClientDto> filterClientNameType(String text1, String text2);

    static ClientDto convertEntityToDtoForClient(ClientEntity clientEntity){
        return ClientDto.builder()
                    .clientId(clientEntity.getClientId())
                    .clientName(clientEntity.getClientName())
                    .type(TypeEnum.getTypeEnum(clientEntity.getType()))
                    .dateAdded(clientEntity.getAdded())
                    .build();

    }

    static ClientEntity convertDtoToEntityForClient(ClientDto clientDto){
        ClientEntity clientEntity = new ClientEntity();
        clientEntity.setClientId(clientDto.getClientId());
        clientEntity.setClientName(clientDto.getClientName());
        clientEntity.setType(clientDto.getType().getTypeValue());
        clientEntity.setAdded(clientDto.getDateAdded());
        return clientEntity;
    }

}
