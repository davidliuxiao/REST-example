package com.roche.product.server.domains;


import javax.persistence.Column;
import javax.persistence.MappedSuperclass;

@MappedSuperclass
public abstract class BasicEntity {
    @Column(name = "is_active")
    protected boolean active = true;

    public abstract Long getId();

}
