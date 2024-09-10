package com.example.dev_j310nam.entity;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;
import java.util.LinkedHashSet;
import java.util.Set;

@Getter
@Setter
@Entity
@EqualsAndHashCode
@ToString
@NoArgsConstructor
@RequiredArgsConstructor
@Table(name = "clients", schema = "client_db")
public class ClientEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "client_id", nullable = false)
    private Integer clientId;

    @NonNull
    @Column(name = "client_name", nullable = false, length = 100)
    private String clientName;

    @NonNull
    @Column(name = "client_type", nullable = false, length = 20)
    private String type;

    @NonNull
    @Column(name = "added", nullable = false)
    private LocalDate added;

    @OneToMany(mappedBy = "client", cascade = {CascadeType.DETACH,CascadeType.MERGE,CascadeType.PERSIST,CascadeType.REFRESH})
    private Set<AddressEntity> addresses = new LinkedHashSet<>();

}