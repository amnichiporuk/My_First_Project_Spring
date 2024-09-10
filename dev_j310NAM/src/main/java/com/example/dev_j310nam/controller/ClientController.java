package com.example.dev_j310nam.controller;

import com.example.dev_j310nam.dto.ClientAddressDto;
import com.example.dev_j310nam.filtering.Filters;
import com.example.dev_j310nam.service.AddressService;
import com.example.dev_j310nam.service.ClientAddressService;
import com.example.dev_j310nam.service.ClientService;
import jakarta.validation.Valid;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import java.rmi.NoSuchObjectException;
import java.time.LocalDate;
import java.util.List;

@Controller
@RequestMapping("/client")
public class ClientController {

    private final ClientAddressService clientAddressService;

    public ClientController(ClientAddressService clientAddressService, ClientService clientService, ClientService clientService1, AddressService addressService) {
        this.clientAddressService = clientAddressService;
    }

    @GetMapping()
    public String findAllClients(@ModelAttribute("filters") Filters filters, Model model){
        String type = filters.getFilterType();
        String nameAddress = filters.getFilterNameAddress();
        List<ClientAddressDto> clients = clientAddressService.filterClientTypeNameAddress(type,nameAddress);
        model.addAttribute("clients", clients);
        return "clients_page";
    }

    @GetMapping("/addNewClient")
    public String addNewClient(Model model){
        ClientAddressDto client = new ClientAddressDto(0," "," ", LocalDate.now(),0," "," "," "," ");
        model.addAttribute("client", client);
        return "client_cu";
    }

    @PostMapping("/saveClient")
    public String saveClient(@Valid @ModelAttribute("client") ClientAddressDto client, BindingResult bindingResult){
        if(bindingResult.hasErrors()){
            return "client_cu";
        } else {
            clientAddressService.saveClient(client);
            return "redirect:/client";
        }
    }

    @GetMapping("/updateClient/{id}")
    public String updateClient(@PathVariable("id") Integer id, Model model) throws NoSuchObjectException {
        clientAddressService.findByIdClient(id).ifPresent(client -> {
            ClientAddressDto clientAddressDto = clientAddressService
                    .convertClientToClientAddressDto(client).findFirst().orElse(null);
            //Адресная заглушка
            clientAddressDto.setAddressId(0);
            clientAddressDto.setIp("000.000.000.000");
            clientAddressDto.setMac("default");
            clientAddressDto.setModel("default");
            clientAddressDto.setAddress("default");
            model.addAttribute("client", clientAddressDto);
        });
        return "client_update";
    }

    @PostMapping("/saveUpdateClient")
    public String saveUpdateClient(@Valid @ModelAttribute("client") ClientAddressDto client, BindingResult bindingResult) throws NoSuchObjectException {
        if(bindingResult.hasErrors()){
            return "client_update";
        } else {
            clientAddressService.updateClient(client);
            return "redirect:/client";
        }
    }

    @GetMapping("/removeClient/{id}")
    public String removeClient(@PathVariable("id") Integer id) throws NoSuchObjectException {
        clientAddressService.removeClientById(id);
        return "redirect:/client";
    }

}
