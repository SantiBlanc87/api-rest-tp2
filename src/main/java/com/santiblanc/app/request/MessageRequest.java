package com.santiblanc.app.request;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.santiblanc.app.entities.User;

/**
 * Created by Blade-Razer on 6/7/2017.
 */
public class MessageRequest {
    //Propiedades
    @JsonProperty("receiver")
    String receiver;
    @JsonProperty("subject")
    String subject;
    @JsonProperty("message")
    String msg;

    //Constructor
    public MessageRequest(){}

    //Getters and Setters
    public String getReceiver() {
        return receiver;
    }

    public void setReceiver(String receiver) {
        this.receiver = receiver;
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
