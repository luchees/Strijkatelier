package com.strike.strijkatelier.model;

import com.google.i18n.phonenumbers.NumberParseException;
import com.google.i18n.phonenumbers.PhoneNumberUtil;
import com.google.i18n.phonenumbers.Phonenumber;
import com.strike.strijkatelier.exception.BadResourceException;
import lombok.Getter;
import lombok.Setter;
import org.apache.commons.validator.routines.EmailValidator;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "customer")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
@Getter
@Setter
public class Customer {
    private static final long serialVersionUID = 4048798961366546485L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @NotBlank
    private String emailaddress;
    @NotBlank
    private String name;
    @NotBlank
    private String phoneNumber;
    @OneToMany(mappedBy = "customer")
    private List<Bucket> buckets;
    private double minutesLeft;
    @Column(length = 4000)
    private String note;

    public Customer() {
    }


    public Customer(String emailaddress, String name, String phoneNumber) throws BadResourceException {
        setEmailAddress(emailaddress);
        setPhoneNumber(phoneNumber);
        this.name = name;
        buckets = new ArrayList<>();
        minutesLeft = 0.00;
    }

    public void addBucket(Bucket bucket) {
        buckets.add(bucket);
    }


    public void setEmailAddress(String emailaddress) throws BadResourceException {
        if (!EmailValidator.getInstance(true).isValid(emailaddress)){
            this.emailaddress=emailaddress;
        }
        else {
            throw new BadResourceException("emailAddress is not valid syntax");
        }
    }
    public void setPhoneNumber(String phoneNumber) throws BadResourceException {
        PhoneNumberUtil phoneNumberUtil = PhoneNumberUtil.getInstance();
        try {
            Phonenumber.PhoneNumber phoneNumber1 = phoneNumberUtil.parse(phoneNumber, "BE");
            this.phoneNumber=phoneNumber;
        } catch (NumberParseException e) {
            throw new BadResourceException("phonenumber is not valid syntax"  );
        }

    }

}
