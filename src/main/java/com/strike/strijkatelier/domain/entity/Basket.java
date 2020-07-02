package com.strike.strijkatelier.domain.entity;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.List;

@Entity
@Table(name = "basket")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
@JsonIdentityInfo(generator= ObjectIdGenerators.PropertyGenerator.class, property="id")
@Getter
@Setter
public class Basket implements Serializable {
    public Basket(){};
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn (name="customer_id")
    private Customer customer;

    public boolean active;
    @Basic
    public LocalDateTime startDateTime;
    @Basic
    public LocalDateTime doneDateTime;
    @ManyToMany(mappedBy="baskets")
    public List<Item> items;

    public double price;
    @NotNull
    public boolean cash;

    public Basket(List<Item> items, boolean cash){
        active=true;
        startDateTime=LocalDateTime.now();
        this.items=items;
        this.cash=cash;
    }






}
