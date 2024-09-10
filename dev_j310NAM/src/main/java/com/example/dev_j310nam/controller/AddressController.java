package com.example.dev_j310nam.controller;

import com.example.dev_j310nam.dto.ClientAddressDto;
import com.example.dev_j310nam.service.ClientAddressService;
import jakarta.validation.Valid;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import java.rmi.NoSuchObjectException;

@Controller
@RequestMapping("/address")
public class AddressController {

    private final ClientAddressService clientAddressService;

    public AddressController(ClientAddressService clientAddressService) {
        this.clientAddressService = clientAddressService;
    }

    @GetMapping("/addNewAddress/{id}")
    public String addNewAddress(@PathVariable("id") Integer id, Model model) throws NoSuchObjectException {
        clientAddressService.findByIdClient(id).ifPresent(clientDto -> {
            ClientAddressDto clientNewAddress = ClientAddressService.clientEmptyAddress(clientDto);
            model.addAttribute("client", clientNewAddress);
        });
        return "address_cu";
    }

    @PostMapping("/saveAddress")
    public String saveAddress(@Valid @ModelAttribute("client") ClientAddressDto client, BindingResult bindingResult) throws NoSuchObjectException {
        if(bindingResult.hasErrors()){
            return "address_cu";
        } else {
            clientAddressService.saveAddress(client);
            return "redirect:/client";
        }
    }

    @GetMapping("/removeAddress/{id}")
    public String removeAddress(@PathVariable("id") Integer id) throws NoSuchObjectException {
        clientAddressService.removeAddressById(id);
        return "redirect:/client";
    }

    @GetMapping("/updateAddress/{id}")
    public String updateAddress(@PathVariable("id") Integer id, Model model) throws NoSuchObjectException {
        clientAddressService.findByIdAddress(id).ifPresent(addressDto -> {
            ClientAddressDto client = ClientAddressService.convertClientAddressDto(addressDto.getClientDto(),addressDto);
            model.addAttribute("client", client);
        });
        return "address_update";
    }

    @PostMapping("/saveUpdateAddress")
    public String saveUpdateAddress(@Valid @ModelAttribute("client") ClientAddressDto client, BindingResult bindingResult) throws NoSuchObjectException {
        if(bindingResult.hasErrors()){
            return "address_cu";
        } else {
            clientAddressService.updateAddress(client);
            return "redirect:/client";
        }
    }

}
