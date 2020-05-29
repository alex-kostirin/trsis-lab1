package ru.guap.shoppinglist.model;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Data
@NoArgsConstructor
@AllArgsConstructor(access = AccessLevel.PROTECTED)
@Entity(name = "lists")
public class List {

    private @Id @GeneratedValue(strategy = GenerationType.IDENTITY) Integer id;

    private String name;

    private @OneToMany(mappedBy = "list", cascade = CascadeType.ALL, orphanRemoval = true) @OrderBy("id") java.util.List<Item> items;

    public List(String name) {
        this(name, null);
    }

    public List(String name, java.util.List<Item> items) {
        this(null, name, items);
    }
}
