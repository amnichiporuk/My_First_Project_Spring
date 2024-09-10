package com.example.dev_j310nam.service.impl;


import com.example.dev_j310nam.dto.ClientDto;
import com.example.dev_j310nam.repository.ClientRepository;
import com.example.dev_j310nam.service.ClientService;
import org.springframework.stereotype.Service;
import java.rmi.NoSuchObjectException;
import java.util.Optional;
import java.util.stream.Stream;

@Service
public class ClientServiceImpl implements ClientService {

    private final ClientRepository clientRepository;

    public ClientServiceImpl(ClientRepository clientRepository) {
        this.clientRepository = clientRepository;
    }

    @Override
    public Optional<ClientDto> create(ClientDto clientDto) {
        return clientRepository.create(ClientService.convertDtoToEntityForClient(clientDto))
                .map(ClientService::convertEntityToDtoForClient);
    }

    @Override
    public void remove(Integer id) throws NoSuchObjectException, UnknownError {
        clientRepository.remove(id);
    }

    @Override
    public void update(ClientDto clientDto) throws NoSuchObjectException, UnknownError {
        clientRepository.update(ClientService.convertDtoToEntityForClient(clientDto));
    }

    @Override
    public Stream<ClientDto> findAll() {
        return clientRepository.findAll().map(ClientService::convertEntityToDtoForClient);
    }

    @Override
    public Optional<ClientDto> findById(Integer arg) throws NoSuchObjectException, UnknownError {
        return clientRepository.findById(arg).map(ClientService::convertEntityToDtoForClient);
    }


    @Override
    public Stream<ClientDto> filterClientName(String text) {
        return clientRepository.filterClientName(text).map(ClientService::convertEntityToDtoForClient);
    }

    @Override
    public Stream<ClientDto> filterClientType(String text){
        return clientRepository.filterClientType(text).map(ClientService::convertEntityToDtoForClient);
    }
    @Override
    public Stream<ClientDto> filterClientNameType(String text1, String text2){
        return clientRepository.filterClientNameType(text1, text2).map(ClientService::convertEntityToDtoForClient);
    }

}
