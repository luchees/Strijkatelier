package com.strike.strijkatelier.domain.entity;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import com.strike.strijkatelier.exception.BadResourceException;
import lombok.Getter;
import lombok.Setter;
import org.apache.commons.validator.routines.EmailValidator;
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
@JsonIdentityInfo(generator= ObjectIdGenerators.PropertyGenerator.class, property="id")
@Getter
@Setter
public class Customer implements Serializable {
    private static final long serialVersionUID = 4048798961366546485L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @NotBlank
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


    public Customer(String emailaddress, String firstName, String lastName, String phoneNumber) throws BadResourceException {
        setEmailAddress(emailaddress);
        setPhoneNumber(phoneNumber);
        this.firstName = firstName;
        this.lastName = lastName;
        baskets = new ArrayList<>();
        minutesLeft = 0.00;
    }

    public void addBasket(Basket basket) {
        baskets.add(basket);
    }


    public void setEmailAddress(String emailaddress) throws BadResourceException {
        if (!EmailValidator.getInstance(true).isValid(emailaddress)){
            this.emailaddress=emailaddress;
        }
        else {
            throw new BadResourceException("emailAddress is not valid syntax");
        }
    }
//    public void setPhoneNumber(String phoneNumber) throws BadResourceException {
//        PhoneNumberUtil phoneNumberUtil = PhoneNumberUtil.getInstance();
//        try {
//            Phonenumber.PhoneNumber phoneNumber1 = phoneNumberUtil.parse(phoneNumber, "BE");
//            this.phoneNumber=phoneNumber;
//        } catch (NumberParseException e) {
//            throw new BadResourceException("phonenumber is not valid syntax"  );
//        }
//
//    }

}
