package com.strike.strijkatelier.domain.entity;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;
import java.util.List;

@Entity
@Table(name = "basket")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
@JsonIdentityInfo(generator= ObjectIdGenerators.PropertyGenerator.class, property="id")
public class Basket {
    public Basket(){};
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn (name="customer_id")
    private Customer customer;

    private boolean active;
    @Basic
    private LocalDateTime startDateTime;
    @Basic
    private LocalDateTime doneDateTime;
    @ManyToMany(mappedBy="baskets")
    private List<Item> items;

    @OneToMany(mappedBy = "basket")
    private List<ServiceCheck> serviceChecks;

    public double price;
    @NotNull
    public boolean cash;

    public Basket(List<Item> items, List<ServiceCheck> serviceChecks, boolean cash){
        this.serviceChecks = serviceChecks;
        active=true;
        startDateTime=LocalDateTime.now();
        this.items=items;
        this.cash=cash;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Customer getCustomer() {
        return customer;
    }

    public void setCustomer(Customer customer) {
        this.customer = customer;
    }

    public boolean isActive() {
        return active;
    }

    public void setActive(boolean active) {
        this.active = active;
    }

    public LocalDateTime getStartDateTime() {
        return startDateTime;
    }

    public void setStartDateTime(LocalDateTime startDateTime) {
        this.startDateTime = startDateTime;
    }

    public LocalDateTime getDoneDateTime() {
        return doneDateTime;
    }

    public void setDoneDateTime(LocalDateTime doneDateTime) {
        this.doneDateTime = doneDateTime;
    }

    public List<Item> getItems() {
        return items;
    }

    public void setItems(List<Item> items) {
        this.items = items;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public boolean isCash() {
        return cash;
    }

    public void setCash(boolean cash) {
        this.cash = cash;
    }

    public List<ServiceCheck> getServiceChecks() {
        return serviceChecks;
    }

    public void setServiceChecks(List<ServiceCheck> serviceChecks) {
        this.serviceChecks = serviceChecks;
    }
}
