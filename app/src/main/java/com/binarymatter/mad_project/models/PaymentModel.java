package com.binarymatter.mad_project.models;

public class PaymentModel {

    String id;
    String CardHolderName;
    String CardNumber;
    String ExpireDate;
    String Cvv;

    public PaymentModel() {}

    public PaymentModel(String id, String cardHolderName, String cardNumber, String expireDate, String cvv) {
        this.id = id;
        CardHolderName = cardHolderName;
        CardNumber = cardNumber;
        ExpireDate = expireDate;
        Cvv = cvv;
    }


    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getCardHolderName() {
        return CardHolderName;
    }

    public void setCardHolderName(String cardHolderName) {
        CardHolderName = cardHolderName;
    }

    public String getCardNumber() {
        return CardNumber;
    }

    public void setCardNumber(String cardNumber) {
        CardNumber = cardNumber;
    }

    public String getExpireDate() {
        return ExpireDate;
    }

    public void setExpireDate(String expireDate) {
        ExpireDate = expireDate;
    }

    public String getCvv() {
        return Cvv;
    }

    public void setCvv(String cvv) {
        Cvv = cvv;
    }
}
