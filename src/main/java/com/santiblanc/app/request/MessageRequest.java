package com.santiblanc.app.request;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.santiblanc.app.entities.User;

/**
 * Created by Blade-Razer on 6/7/2017.
 */
public class MessageRequest {
    //Propiedades
    @JsonProperty("sender")
    User sender;
    @JsonProperty("receiver")
    User receiver;
    @JsonProperty("date")
    String date;
    @JsonProperty("subject")
    String subject;
    @JsonProperty("message")
    String msg;
    @JsonProperty("erased")
    boolean status;

    //Constructor
    public MessageRequest(){}

    //Getters and Setters
    public User getSender() {
        return sender;
    }

    public void setSender(User sender) {
        this.sender = sender;
    }

    public User getReceiver() {
        return receiver;
    }

    public void setReceiver(User receiver) {
        this.receiver = receiver;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getSubject() {
        return subject;
    }

    public void setSubject(String subject) {
        this.subject = subject;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public boolean isStatus() {
        return status;
    }

    public void setStatus(boolean status) {
        this.status = status;
    }
}
