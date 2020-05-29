package ru.guap.shoppinglist;

import ru.guap.shoppinglist.dao.Dao;
import ru.guap.shoppinglist.dao.JpaItemDao;
import ru.guap.shoppinglist.dao.JpaListDao;
import ru.guap.shoppinglist.model.Item;
import ru.guap.shoppinglist.model.List;

import javax.persistence.EntityManager;
import javax.persistence.Persistence;
import javax.persistence.PersistenceContext;
import java.util.Optional;

public class Storage {
    private final Dao<List> listDao;
    private final Dao<Item> itemDao;

    @PersistenceContext
    private final EntityManager entityManager;

    public Storage() {
        try {
            Class.forName("org.h2.Driver");
        } catch (Exception e) {
        }
        var factory = Persistence.createEntityManagerFactory("ShoppingListsDB");
        this.entityManager = factory.createEntityManager();
        this.listDao = new JpaListDao(this.entityManager);
        this.itemDao = new JpaItemDao(this.entityManager);
    }

    public Optional<Item> findItem(int id) {
        return this.itemDao.get(id);
    }

    public java.util.List<Item> findAllItems() {
        return this.itemDao.getAll();
    }

    public void saveItem(Item item) {
        this.itemDao.save(item);
    }

    public void updateItem(Item item) {
        this.itemDao.update(item);
    }

    public void deleteItem(Item item) {
        this.itemDao.delete(item);
    }

    public Optional<List> findList(int id) {
        return this.listDao.get(id);
    }

    public java.util.List<List> findAllLists() {
        return this.listDao.getAll();
    }

    public void saveList(List list) {
        this.listDao.save(list);
    }

    public void updateList(List list) {
        this.listDao.update(list);
    }

    public void deleteList(List list) {
        this.listDao.delete(list);
    }

    public void close() {
        this.entityManager.close();
    }
}
