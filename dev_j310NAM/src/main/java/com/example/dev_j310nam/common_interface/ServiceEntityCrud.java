package com.example.dev_j310nam.common_interface;

import java.rmi.NoSuchObjectException;
import java.util.Optional;
import java.util.stream.Stream;

public interface ServiceEntityCrud<T,U>{

    Optional<T> create(T difclass);
    void remove (U arg) throws NoSuchObjectException, UnknownError;
    void update (T difclass) throws NoSuchObjectException, UnknownError;
    Stream<T> findAll();
    Optional<T> findById(U arg) throws NoSuchObjectException, UnknownError;


}
