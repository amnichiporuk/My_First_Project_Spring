package com.example.dev_j310nam.entity;

import jakarta.persistence.*;
import lombok.*;


@Getter
@Setter
@Entity
@EqualsAndHashCode
@ToString
@Table(name = "addresses", schema = "client_db")
public class AddressEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "address_id", nullable = false)
    private Integer addressId;

    @NonNull
    @Column(name = "ip", nullable = false, length = 25)
    private String ip;

    @NonNull
    @Column(name = "mac", nullable = false, length = 20)
    private String mac;

    @NonNull
    @Column(name = "model", nullable = false, length = 100)
    private String model;

    @NonNull
    @Column(name = "address", nullable = false, length = 200)
    private String address;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "client_id")
    private com.example.dev_j310nam.entity.ClientEntity client;

}