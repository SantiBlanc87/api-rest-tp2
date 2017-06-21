package com.santiblanc.app.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import org.joda.time.DateTime;

import java.sql.Timestamp;

/**
 * Created by Blade-Razer on 6/7/2017.
 */
public class MessageWrapper {
    //Propiedades
    @JsonProperty("id")
    Long id;
    @JsonProperty("de")
    String from;
    @JsonProperty("emisor")
    String sender;
    @JsonProperty("receptor")
    String receiver;
    @JsonProperty("fecha")
    DateTime date;
    @JsonProperty("asunto")
    String subject;
    @JsonProperty("mensaje")
    String msg;

    //Constructor
    public MessageWrapper() {
    }

    //Getters and Setters

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

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

    public DateTime getDate() {
        return date;
    }

    public void setDate(DateTime date) {
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

    public String getFrom() {
        return from;
    }

    public void setFrom(String from) {
        this.from = from;
    }
}
