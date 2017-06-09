package com.santiblanc.app.entities;

import javax.persistence.*;
import java.sql.Timestamp;

/**
 * Created by Blade-Razer on 6/5/2017.
 */
@Entity
@Table(name = "messages")
public class Message {
    //Atributos
    @Id
    @GeneratedValue(strategy= GenerationType.AUTO)
    private Long id;

    @OneToOne
    private User sender;

    @OneToOne
    private User receiver;

    private Timestamp date;
    private String subject;
    @Column(name = "msg", columnDefinition = "TEXT")
    private String msg;
    private boolean erased;

    //Constructor
    public Message(){}

    //Getters and Setters
    public Long getId() {
        return id;
    }

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

    public Timestamp getDate() {
        return date;
    }

    public void setDate(Timestamp date) {
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
