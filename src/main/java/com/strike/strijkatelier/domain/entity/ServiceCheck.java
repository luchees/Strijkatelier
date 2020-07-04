package com.strike.strijkatelier.domain.entity;


import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.time.LocalDate;

@Entity
@Table(name = "service_check")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
@JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class, property = "id")
public class ServiceCheck {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @NotNull
    private String serviceCheckNumber;

    @Basic
    private LocalDate usedDate;

    private LocalDate expiryDate;
    @NotNull
    private boolean signed;

    @ManyToOne(fetch = FetchType.EAGER)
    private Basket basket;

    public ServiceCheck(){};

    public ServiceCheck(String serviceCheckNumber, LocalDate usedDate, LocalDate expiryDate, boolean signed, Basket basket) {
        this.serviceCheckNumber=serviceCheckNumber;
        this.usedDate=usedDate;
        this.expiryDate=expiryDate;
        this.signed=signed;
        this.basket=basket;
    }


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getServiceCheckNumber() {
        return serviceCheckNumber;
    }

    public void setServiceCheckNumber(String serviceCheckNumber) {
        this.serviceCheckNumber = serviceCheckNumber;
    }

    public LocalDate getUsedDate() {
        return usedDate;
    }

    public void setUsedDate(LocalDate usedDate) {
        this.usedDate = usedDate;
    }

    public LocalDate getExpiryDate() {
        return expiryDate;
    }

    public void setExpiryDate(LocalDate expiryDate) {
        this.expiryDate = expiryDate;
    }

    public boolean isSigned() {
        return signed;
    }

    public void setSigned(boolean signed) {
        this.signed = signed;
    }

    public Basket getBasket() {
        return basket;
    }

    public void setBasket(Basket basket) {
        this.basket = basket;
    }
}
