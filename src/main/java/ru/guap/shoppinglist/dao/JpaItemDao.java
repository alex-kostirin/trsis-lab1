package ru.guap.shoppinglist.dao;

import org.jetbrains.annotations.NotNull;
import ru.guap.shoppinglist.model.Item;

import javax.persistence.EntityManager;
import java.util.Optional;
import java.util.function.Consumer;

public class JpaItemDao implements Dao<Item> {
    private final EntityManager entityManager;

    public JpaItemDao(@NotNull EntityManager entityManager) {
        this.entityManager = entityManager;
    }

    @Override
    public Optional<Item> get(int id) {
        var entity =  Optional.ofNullable(entityManager.find(Item.class, id));
        entity.ifPresent(entityManager::refresh);
        return entity;
    }

    @Override
    public java.util.List<Item> getAll() {
        var query = entityManager.createQuery("select e from items e order by id");
        return query.getResultList();
    }

    @Override
    public void save(Item item) {
        executeInsideTransaction(entityManager -> entityManager.persist(item));
    }

    @Override
    public void update(Item item) {
        executeInsideTransaction(entityManager -> entityManager.merge(item));
    }

    @Override
    public void delete(Item item) {
        item.setList(null);
        executeInsideTransaction(entityManager -> entityManager.remove(item));
    }

    private void executeInsideTransaction(Consumer<EntityManager> action) {
        var tx = entityManager.getTransaction();
        try {
            tx.begin();
            action.accept(entityManager);
            tx.commit();
        } catch (RuntimeException e) {
            tx.rollback();
            throw e;
        }
    }
}
