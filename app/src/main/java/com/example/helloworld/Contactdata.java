package com.example.helloworld;

public class Contactdata {
    String cId;
    String cName;
    String cEmail;
    String cFeedback;
    String cMobile;

    public Contactdata()
    {

    }

    public Contactdata(String cId, String cName, String cEmail, String cFeedback, String cMobile) {
        this.cId = cId;
        this.cName = cName;
        this.cEmail = cEmail;
        this.cFeedback = cFeedback;
        this.cMobile = cMobile;
    }

    public String getcId() {
        return cId;
    }

    public String getcName() {
        return cName;
    }

    public String getcEmail() {
        return cEmail;
    }

    public String getcFeedback() {
        return cFeedback;
    }

    public String getcMobile() {
        return cMobile;
    }
}
