package com.strike.strijkatelier.model;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import java.time.LocalDateTime;
import java.util.List;

@Entity
@Table(name = "bucket")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
@Getter
@Setter
public class Bucket {
    public Bucket(){};
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
    @OneToMany(mappedBy="bucket")
    public List<Item> items;
    public double price;
    public boolean cash;

    public Bucket( List<Item> items, boolean cash){
        active=true;
        startDateTime=LocalDateTime.now();
        this.items=items;
        this.cash=cash;
    }






}
