package com.example.dev_j310nam.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.*;
import java.time.LocalDate;

@Data
@Builder
@EqualsAndHashCode(exclude = {"clientName", "type", "dateAdded", "model", "address"})

public class ClientAddressDto {

    private Integer clientId;

    @NotBlank(message = "Имя клиента не должно быть пустым!")
    @Size(max = 200, message = "Имя клиента не должно содержать более 200 символов!")
    private String clientName;

    @NotNull(message = "Тип клиента не должен быть пустым!")
    @Size(max = 20, message = "Тип клиента не должен содержать более 20 символов!")
    private String type;

    private LocalDate dateAdded;

    private Integer addressId;

    @Pattern(regexp = "\\d{3}.\\d{3}.\\d{3}.\\d{3}", message = "Введите IP в соответствии со следующим шаблоном XXX.XXX.XXX.XXX")
    private String ip;

//    @Pattern(regexp = "([0-9A-Fa-f]{2}[:-]){5}([0-9A-Fa-f]{2})", message = "MAC адрес не соответствует шаблону!")
    @NotBlank(message = "MAC клиента не должен быть пустым!")
    @Size(max = 20, message = "MAC клиента не должен содержать более 20 символов!")
    private String mac;

    @NotBlank(message = "Наименование модели не должно быть пустым!")
    @Size(max = 100, message = "Наименование модели не должно содержать более 100 символов!")
    private String model;

    @NotBlank(message = "Адрес не должен быть пустым!")
    @Size(max = 200, message = "Адрес не должен содержать более 200 символов!")
    private String address;

    public ClientAddressDto(Integer clientId, String clientName, String type, LocalDate dateAdded,
                            Integer addressId, String ip, String mac, String model, String address) {
        this.clientId = clientId;
        this.clientName = clientName;
        this.type = type;
        this.dateAdded = dateAdded;
        this.addressId = addressId;
        this.ip = ip;
        this.mac = mac;
        this.model = model;
        this.address = address;
    }

}
