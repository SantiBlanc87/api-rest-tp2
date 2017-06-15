package com.santiblanc.app.response;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.sql.Timestamp;

/**
 * Created by Blade-Razer on 6/7/2017.
 */
public class MessageWrapper {
    //Propiedades
    @JsonProperty("emisor")
    String sender;
    @JsonProperty("receptor")
    String receiver;
    @JsonProperty("fecha")
    String date;
    @JsonProperty("asunto")
    String subject;
    @JsonProperty("mensaje")
    String msg;

    //Constructor
    public MessageWrapper(){}

    //Getters and Setters

    public String getSender() {
        return sender;
    }

    public void setSender(String sender) {
        this.sender = sender;
    }

    public String getReceiver() {
        return receiver;
    }

    public void setReceiver(String receiver) {
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
}
