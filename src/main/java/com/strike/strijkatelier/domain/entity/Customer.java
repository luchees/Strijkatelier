package com.strike.strijkatelier.domain.entity;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "customer")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
@JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class, property = "id")
public class Customer implements Serializable {
    private static final long serialVersionUID = 4048798961366546485L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true)
    private String emailaddress;
    @NotBlank
    private String firstName;
    @NotBlank
    private String lastName;
    @NotBlank
    private String phoneNumber;

    @OneToMany(mappedBy = "customer", fetch = FetchType.EAGER)
    private List<Basket> baskets;
    private double minutesLeft;
    @Column(length = 4000)
    private String note;

    public Customer() {
    }


    public Customer(String emailaddress, String firstName, String lastName, String phoneNumber) {
        setEmailaddress(emailaddress);
        setPhoneNumber(phoneNumber);
        this.firstName = firstName;
        this.lastName = lastName;
        baskets = new ArrayList<>();
        minutesLeft = 0.00;
    }

    public void addBasket(Basket basket) {
        baskets.add(basket);
    }

    public static long getSerialVersionUID() {
        return serialVersionUID;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getEmailaddress() {
        return emailaddress;
    }

    public void setEmailaddress(String emailaddress) {
        this.emailaddress = emailaddress;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public List<Basket> getBaskets() {
        return baskets;
    }

    public void setBaskets(List<Basket> baskets) {
        this.baskets = baskets;
    }

    public double getMinutesLeft() {
        return minutesLeft;
    }

    public void setMinutesLeft(double minutesLeft) {
        this.minutesLeft = minutesLeft;
    }

    public String getNote() {
        return note;
    }

    public void setNote(String note) {
        this.note = note;
    }
}
