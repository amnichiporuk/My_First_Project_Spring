package com.example.dev_j310nam.repository.impl;

import com.example.dev_j310nam.entity.AddressEntity;
import com.example.dev_j310nam.entity.ClientEntity;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaDelete;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Root;
import org.springframework.stereotype.Repository;

import java.rmi.NoSuchObjectException;
import java.util.Optional;
import java.util.stream.Stream;

@Repository
public class ClientRepositoryImpl implements com.example.dev_j310nam.repository.ClientRepository {

    @PersistenceContext
    EntityManager entityManager;

    @Override
    public Optional<ClientEntity> create(ClientEntity clientEntity) {
        entityManager.persist(clientEntity);
        return Optional.of(clientEntity);
    }

    @Override
    public void remove(Integer id) throws NoSuchObjectException, UnknownError {
        CriteriaBuilder cbAddress = entityManager.getCriteriaBuilder();
        CriteriaDelete<AddressEntity> cdAddress = cbAddress.createCriteriaDelete(AddressEntity.class);
        Root<AddressEntity> rootAddress = cdAddress.from(AddressEntity.class);
        findById(id).ifPresent(clientEntity ->
                cdAddress.where(cbAddress.equal(rootAddress.get("client"), clientEntity)));
        entityManager.createQuery(cdAddress).executeUpdate();

        CriteriaBuilder cbClient = entityManager.getCriteriaBuilder();
        CriteriaDelete<ClientEntity> cdClient = cbClient.createCriteriaDelete(ClientEntity.class);
        Root<ClientEntity> root = cdClient.from(ClientEntity.class);
        cdClient.where(cbClient.equal(root.get("clientId"),id));
        entityManager.createQuery(cdClient).executeUpdate();
//        findById(id).ifPresent(clientEntity -> entityManager.remove(clientEntity));
    }

    @Override
    public void update(ClientEntity clientEntity) throws NoSuchObjectException, UnknownError {
        System.out.println("Клиент обновился "+clientEntity.getClientId());
        Integer id = clientEntity.getClientId();
        Optional<ClientEntity> client = Optional.of(id).map(clientId -> entityManager.find(ClientEntity.class,clientId));
        client.ifPresent(entity ->{
            entity.setClientId(clientEntity.getClientId());
            entity.setClientName(clientEntity.getClientName());
            entity.setType(clientEntity.getType());
            entity.setAdded(clientEntity.getAdded());
            entityManager.merge(entity);
            entityManager.flush();
        });
    }

    @Override
    public Stream<ClientEntity> findAll() {
        CriteriaBuilder cbClient = entityManager.getCriteriaBuilder();
        CriteriaQuery<ClientEntity> cqClient = cbClient.createQuery(ClientEntity.class);
        cqClient.select(cqClient.from(ClientEntity.class));
        return  entityManager.createQuery(cqClient).getResultList().stream();
    }

    @Override
    public Optional<ClientEntity> findById(Integer id) throws NoSuchObjectException, UnknownError {
        return Optional.of(id).map(clientId -> entityManager.find(ClientEntity.class, clientId));
    }


    @Override
    public Stream<ClientEntity> filterClientName(String text) {
        CriteriaBuilder cb = entityManager.getCriteriaBuilder();
        CriteriaQuery<ClientEntity> cq = cb.createQuery(ClientEntity.class);
        Root<ClientEntity> root = cq.from(ClientEntity.class);
        cq.select(root).where(cb.like(root.get("clientName"), "%"+text+"%"));
        return entityManager.createQuery(cq).getResultList().stream();
    }

    @Override
    public Stream<ClientEntity> filterClientType(String text) {
        CriteriaBuilder cb = entityManager.getCriteriaBuilder();
        CriteriaQuery<ClientEntity> cq = cb.createQuery(ClientEntity.class);
        Root<ClientEntity> root = cq.from(ClientEntity.class);
        cq.select(root).where(cb.like(root.get("type"), "%"+text+"%"));
        return entityManager.createQuery(cq).getResultList().stream();
    }

    @Override
    public Stream<ClientEntity> filterClientNameType(String text1, String text2) {
        CriteriaBuilder cb = entityManager.getCriteriaBuilder();
        CriteriaQuery<ClientEntity> cq = cb.createQuery(ClientEntity.class);
        Root<ClientEntity> root = cq.from(ClientEntity.class);
        cq.select(root).where(
                cb.or(
                        cb.like(root.get("clientName"), "%"+text1+"%"),
                        cb.like(root.get("type"), "%"+text2+"%")
                )
        );
        return entityManager.createQuery(cq).getResultList().stream();
    }

}
