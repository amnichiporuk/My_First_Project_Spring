package com.example.dev_j310nam.dto;

import lombok.*;
import java.time.LocalDate;


@Data
@Builder
@EqualsAndHashCode(exclude = {"clientName", "type", "dateAdded"})
public class ClientDto {

    private Integer clientId;
    private String clientName;
    private TypeEnum type;
    private LocalDate dateAdded;

}
