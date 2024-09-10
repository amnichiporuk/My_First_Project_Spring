package com.example.dev_j310nam.repository.impl;
import com.example.dev_j310nam.entity.AddressEntity;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Root;
import org.springframework.stereotype.Repository;
import java.rmi.NoSuchObjectException;
import java.util.Optional;
import java.util.stream.Stream;

@Repository
public class AddressRepositoryImpl implements com.example.dev_j310nam.repository.AddressRepository {

    @PersistenceContext
    EntityManager entityManager;

    @Override
    public Optional<AddressEntity> create(AddressEntity addressEntity) {
        entityManager.persist(addressEntity);
        return Optional.of(addressEntity);
    }

    @Override
    public void remove(Integer id) throws NoSuchObjectException, UnknownError {
        findById(id).ifPresent(addressEntity -> entityManager.remove(addressEntity));
    }

    @Override
    public void update(AddressEntity addressEntity) throws NoSuchObjectException, UnknownError {
        Integer id = addressEntity.getAddressId();
        Optional<AddressEntity> address = Optional.of(id).map(addressId -> entityManager.find(AddressEntity.class,addressId));
        address.ifPresent(entity -> {
            entity.setAddressId(addressEntity.getAddressId());
            entity.setIp(addressEntity.getIp());
            entity.setMac(addressEntity.getMac());
            entity.setModel(addressEntity.getModel());
            entity.setAddress(addressEntity.getAddress());
            entity.setClient(addressEntity.getClient());
            entityManager.merge(entity);
            entityManager.flush();
        });
    }

    @Override
    public Stream<AddressEntity> findAll() {
        return entityManager.createNativeQuery("SELECT * FROM addresses", AddressEntity.class).getResultList().stream();
    }

    @Override
    public Optional<AddressEntity> findById(Integer id) throws NoSuchObjectException, UnknownError {
        return Optional.of(id).map(addressId -> entityManager.find(AddressEntity.class, addressId));
    }
    @Override
    public Stream<AddressEntity> findAddressesEntityByClientId(Integer id) {
        String query = "SELECT * FROM addresses WHERE client_id="+id;
        return entityManager.createNativeQuery(query, AddressEntity.class).getResultList().stream();
    }

    public Stream<AddressEntity> filterAddressOfClient(String text){
        CriteriaBuilder cb = entityManager.getCriteriaBuilder();
        CriteriaQuery<AddressEntity> cq = cb.createQuery(AddressEntity.class);
        Root<AddressEntity> root = cq.from(AddressEntity.class);
        cq.select(root).where(cb.like(root.get("address"), "%"+text+"%"));
        return entityManager.createQuery(cq).getResultList().stream();
    }

}
