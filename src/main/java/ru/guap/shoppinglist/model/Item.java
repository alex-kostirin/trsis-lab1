package ru.guap.shoppinglist.model;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Data
@NoArgsConstructor
@AllArgsConstructor(access = AccessLevel.PROTECTED)
@Entity(name = "items")
public class Item {
    private @Id @GeneratedValue(strategy = GenerationType.IDENTITY) Integer id;

    private String name;

    private int count;

    private @ManyToOne(fetch = FetchType.LAZY) @JoinColumn(name = "list_id") List list;


    public Item(String name, int count) {
        this(name, count, null);
    }

    public Item(String name, int count, List list) {
        this(null, name, count, list);
    }
}
