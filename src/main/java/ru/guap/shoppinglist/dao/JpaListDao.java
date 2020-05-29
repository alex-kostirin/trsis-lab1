package ru.guap.shoppinglist.dao;

import org.jetbrains.annotations.NotNull;
import ru.guap.shoppinglist.model.List;

import javax.persistence.EntityManager;
import java.util.Optional;
import java.util.function.Consumer;

public class JpaListDao implements Dao<List>{

    private final EntityManager entityManager;

    public JpaListDao(@NotNull EntityManager entityManager) {
        this.entityManager = entityManager;
    }

    @Override
    public Optional<List> get(int id) {
        var entity = Optional.ofNullable(entityManager.find(List.class, id));
        entity.ifPresent(entityManager::refresh);
        return entity;
    }

    @Override
    public java.util.List<List> getAll() {
        var query = entityManager.createQuery("select e from lists e order by id");
        return query.getResultList();
    }

    @Override
    public void save(List list) {
        executeInsideTransaction(entityManager -> entityManager.persist(list));
    }

    @Override
    public void update(List list) {
        executeInsideTransaction(entityManager -> entityManager.merge(list));
    }

    @Override
    public void delete(List list) {
        executeInsideTransaction(entityManager -> entityManager.remove(list));
    }

    private void executeInsideTransaction(Consumer<EntityManager> action) {
        var tx = entityManager.getTransaction();
        try {
            tx.begin();
            action.accept(entityManager);
            tx.commit();
        }
        catch (RuntimeException e) {
            tx.rollback();
            throw e;
        }
    }
}
