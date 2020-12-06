package com.roche.product.server.domains.product;

import com.roche.product.server.domains.BasicEntity;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Objects;

@Entity
@SequenceGenerator(name = "seq-roche-products", sequenceName = "SEQ_ROCHE_PRODUCT_ID", initialValue = 1, allocationSize = 50)
public class Product extends BasicEntity implements Serializable {

    @Id
    @GeneratedValue(generator = "seq-roche-products")
    private  Long id;
    private  String name;
    private  BigDecimal price;
    private  LocalDate date;

    //Needed when instantiating the entity from a JDBC ResultSet
    private Product() {
    }

    public Product(String name, BigDecimal price, LocalDate date) {
        this.name = name;
        this.price = price;
        this.date = date;
    }

    public Product(Long id, String name, BigDecimal price, LocalDate date) {
        this.id = id;
        this.name = name;
        this.price = price;
        this.date = date;
    }

    @Override
    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public LocalDate getDate() {
        return date;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Product product = (Product) o;
        return Objects.equals(id, product.id) &&
                Objects.equals(name, product.name) &&
                Objects.equals(price, product.price) &&
                Objects.equals(date, product.date);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name, price, date);
    }

    @Override
    public String toString() {
        return "Product{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", price=" + price +
                ", date=" + date +
                '}';
    }
}
