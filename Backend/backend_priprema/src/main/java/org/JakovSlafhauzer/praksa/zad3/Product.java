package org.JakovSlafhauzer.praksa.zad3;

import io.quarkus.hibernate.orm.panache.PanacheEntity;
import jakarta.persistence.Entity;

@Entity
public class Product extends PanacheEntity {
    public String name;
    public double price;
    public String category;
}
