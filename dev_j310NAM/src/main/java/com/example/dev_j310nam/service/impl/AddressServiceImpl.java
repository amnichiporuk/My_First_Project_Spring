package com.example.dev_j310nam.service.impl;

import com.example.dev_j310nam.dto.AddressDto;
import com.example.dev_j310nam.repository.AddressRepository;
import com.example.dev_j310nam.service.AddressService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.rmi.NoSuchObjectException;
import java.util.Optional;
import java.util.stream.Stream;

@Service
public class AddressServiceImpl implements AddressService {

    private final AddressRepository addressRepository;

    public AddressServiceImpl(AddressRepository addressRepository) {
        this.addressRepository = addressRepository;
    }

    @Override
    public Optional<AddressDto> create(AddressDto addressDto) {
        Optional<AddressDto> address = addressRepository.create(AddressService.convertDtoToEntityForAddress(addressDto))
                .map(AddressService::convertEntityToDtoForAddress);
        return address;
    }

    @Override
    public void remove(Integer id) throws NoSuchObjectException, UnknownError {
        addressRepository.remove(id);
    }

    @Override
    public void update(AddressDto addressDto) throws NoSuchObjectException, UnknownError {
        addressRepository.update(AddressService.convertDtoToEntityForAddress(addressDto));
    }

    @Override
    @Transactional
    public Stream<AddressDto> findAll() {
        return addressRepository.findAll().map(AddressService::convertEntityToDtoForAddress);
    }

    @Override
    public Optional<AddressDto> findById(Integer arg) throws NoSuchObjectException, UnknownError {
        return addressRepository.findById(arg).map(AddressService::convertEntityToDtoForAddress);
    }

    @Override
    public Stream<AddressDto> findAddressesDtoByClientId(Integer id) {
        return addressRepository.findAddressesEntityByClientId(id).map(AddressService::convertEntityToDtoForAddress);
    }

    public Stream<AddressDto> filterAddressOfClient(String text){
        return addressRepository.filterAddressOfClient(text).map(AddressService::convertEntityToDtoForAddress);
    }


}
