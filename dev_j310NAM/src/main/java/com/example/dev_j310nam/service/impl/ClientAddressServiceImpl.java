package com.example.dev_j310nam.service.impl;

import com.example.dev_j310nam.dto.AddressDto;
import com.example.dev_j310nam.dto.ClientAddressDto;
import com.example.dev_j310nam.dto.ClientDto;
import com.example.dev_j310nam.entity.AddressEntity;
import com.example.dev_j310nam.entity.ClientEntity;
import com.example.dev_j310nam.repository.AddressRepository;
import com.example.dev_j310nam.repository.ClientRepository;
import com.example.dev_j310nam.service.ClientAddressService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.rmi.NoSuchObjectException;
import java.util.*;
import java.util.stream.Stream;



@Service
public class ClientAddressServiceImpl implements ClientAddressService {

    private final ClientServiceImpl clientService;
    private final AddressServiceImpl addressService;
    private final ClientRepository clientRepository;
    private final AddressRepository addressRepository;

    public ClientAddressServiceImpl(ClientServiceImpl clientService, AddressServiceImpl addressService, ClientRepository clientRepository, AddressRepository addressRepository) {
        this.clientService = clientService;
        this.addressService = addressService;
        this.clientRepository = clientRepository;
        this.addressRepository = addressRepository;
    }

    @Override
    @Transactional
    public Stream<ClientAddressDto> findAllClients() {
        return clientService.findAll().flatMap(this::convertClientToClientAddressDto);
    }

    //Метод фильтрации по типу клиента
    @Override
    @Transactional
    public Stream<ClientAddressDto> filterClientType(String text){
        return clientService.filterClientType(text).flatMap(this::convertClientToClientAddressDto);
    }

    //Метод фильтрации по имени клиента
    @Override
    @Transactional
    public Stream<ClientAddressDto> filterClientName(String text){
        return clientService.filterClientName(text).flatMap(this::convertClientToClientAddressDto);
    }

    //Метод фильтрации по имени и типу клиента
    @Override
    @Transactional
    public Stream<ClientAddressDto> filterClientNameType(String text1, String text2){
        return clientService.filterClientNameType(text1, text2).flatMap(this::convertClientToClientAddressDto);
    }

    //Метод фильтрации по адресу клиента
    @Override
    @Transactional
    public Stream <ClientAddressDto> filterAddressOfClient(String text){
        return addressService.filterAddressOfClient(text).map(addressDto ->
                ClientAddressService.convertClientAddressDto(addressDto.getClientDto(),addressDto)
        );
    }

    //Метод фильтрации по типу, имени и адресу клиента (ПЕРЕСМОТРЕТЬ РЕАЛИЗАЦИЮ ДАННОГО МЕТОДА)
    @Override
    public List<ClientAddressDto> filterClientTypeNameAddress(String type, String nameAddress){
        List<ClientAddressDto> clients = new ArrayList<>();
        if(type!=null && !type.isEmpty()) {
            if (nameAddress != null && !nameAddress.isEmpty()) {
                Stream<ClientAddressDto> clients1 = filterClientType(type);
                clients = clients1
                        .filter(cad -> (cad.getClientName() != null && cad.getClientName().toLowerCase().contains(nameAddress)) ||
                                (cad.getAddress() != null && cad.getAddress().toLowerCase().contains(nameAddress)))
                        .distinct().toList();
                return clients;
            } else {
                List<ClientAddressDto> clients1 = filterClientType(type).toList();
                return clients1;
            }
        } else if(nameAddress != null && !nameAddress.isEmpty()){
            Stream<ClientAddressDto> clients1 = filterClientName(nameAddress);
            Stream<ClientAddressDto> clients2 = filterAddressOfClient(nameAddress);
            return Stream.concat(clients1, clients2).distinct().toList();
        } else {
            return findAllClients().toList();
        }
    }

    @Override
    @Transactional
    public void saveClient(ClientAddressDto client) {
        ClientEntity clientEntity = new ClientEntity();
        clientEntity.setClientName(client.getClientName());
        clientEntity.setType(client.getType());
        clientEntity.setAdded(client.getDateAdded());
        clientRepository.create(clientEntity);

        AddressEntity addressEntity = new AddressEntity();

        addressEntity.setIp(client.getIp());
        addressEntity.setMac(client.getMac());
        addressEntity.setModel(client.getModel());
        addressEntity.setAddress(client.getAddress());
        addressEntity.setClient(clientEntity);
        addressRepository.create(addressEntity);
    }

    @Override
    @Transactional
    public void saveAddress(ClientAddressDto client) throws NoSuchObjectException {

        clientRepository.findById(client.getClientId()).ifPresent(clientEntity -> {

            AddressEntity addressEntity = new AddressEntity();

            addressEntity.setIp(client.getIp());
            addressEntity.setMac(client.getMac());
            addressEntity.setModel(client.getModel());
            addressEntity.setAddress(client.getAddress());
            addressEntity.setClient(clientEntity);
            addressRepository.create(addressEntity);
        });

    }

    @Override
    @Transactional
    public void removeClientById(Integer id) throws NoSuchObjectException {
        clientService.findById(id).ifPresent(clientDto -> {
            addressService.findAll().forEach(addressDto -> {
                if(clientDto.equals(addressDto.getClientDto())) {
                    try {
                        addressService.remove(addressDto.getAddressId());
                    } catch (NoSuchObjectException e) {
                        throw new RuntimeException(e);
                    }
                }
            });
            try {
                clientService.remove(id);
            } catch (NoSuchObjectException e) {
                throw new RuntimeException(e);
            }
        });
    }

    @Override
    @Transactional
    public void removeAddressById(Integer id) throws NoSuchObjectException {
        addressService.remove(id);
    }

    @Override
    @Transactional
    public void updateClient(ClientAddressDto clientAddressDto) throws NoSuchObjectException {
        clientService.update(ClientAddressService.convertClientAddressDtoToClientDto(clientAddressDto));
    };

    @Override
    @Transactional
    public void updateAddress(ClientAddressDto clientAddressDto) throws NoSuchObjectException{

        clientService.findById(clientAddressDto.getClientId()).ifPresent(clientDto -> {
            AddressDto addressDto = AddressDto.builder()
                    .addressId(clientAddressDto.getAddressId())
                    .ip(clientAddressDto.getIp())
                    .mac(clientAddressDto.getMac())
                    .model(clientAddressDto.getModel())
                    .address(clientAddressDto.getAddress())
                    .clientDto(clientDto)
                    .build();
            try {
                addressService.update(addressDto);
            } catch (NoSuchObjectException e) {
                throw new RuntimeException(e);
            }
        });

    }

    @Override
    @Transactional
    public Optional<ClientDto> findByIdClient(Integer id) throws NoSuchObjectException {
        return clientService.findById(id);
    }

    @Override
    @Transactional
    public Optional<AddressDto> findByIdAddress(Integer id) throws NoSuchObjectException {
        return addressService.findById(id);
    }

    @Override
    @Transactional
    public Stream <ClientAddressDto> convertClientToClientAddressDto(ClientDto clientDto){
        if(addressService.findAddressesDtoByClientId(clientDto.getClientId()).count() == 0){
            return Stream.of(ClientAddressService.convertClientAddressDto(clientDto, null));
        }
        else {
            return addressService.findAddressesDtoByClientId(clientDto.getClientId())
                    .map(addressDto -> ClientAddressService.convertClientAddressDto(clientDto,addressDto));
        }

    }

}
