package com.example.model;

import javax.persistence.*;
import java.time.LocalDate;

@Entity
@Table(name="credit_card")
public class CreditCard {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String cardholder;

    private String number;

    private String ccv;

    @Column(name="expiration_date")
    private LocalDate expirationDate;

    public CreditCard() {
    }

    public CreditCard(String cardholder, String number, String ccv, LocalDate expirationDate) {
        this.cardholder = cardholder;
        this.number = number;
        this.ccv = ccv;
        this.expirationDate = expirationDate;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getCardholder() {
        return cardholder;
    }

    public void setCardholder(String cardholder) {
        this.cardholder = cardholder;
    }

    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number = number;
    }

    public String getCcv() {
        return ccv;
    }

    public void setCcv(String ccv) {
        this.ccv = ccv;
    }

    public LocalDate getExpirationDate() {
        return expirationDate;
    }

    public void setExpirationDate(LocalDate expirationDate) {
        this.expirationDate = expirationDate;
    }

    @Override
    public String toString() {
        return "CreditCard{" +
                "id=" + id +
                ", cardholder='" + cardholder + '\'' +
                ", number='" + number + '\'' +
                ", ccv='" + ccv + '\'' +
                ", expirationDate=" + expirationDate +
                '}';
    }
}
