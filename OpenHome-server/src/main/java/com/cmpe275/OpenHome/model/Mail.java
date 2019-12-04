package com.cmpe275.OpenHome.model;

public class Mail {
    private String text;
    private String subject;
    private String email;

    public Mail(String text, String subject, String email) {
        this.text = text;
        this.subject = subject;
        this.email = email;
    }


    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public String getSubject() {
        return subject;
    }

    public void setSubject(String subject) {
        this.subject = subject;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
}
