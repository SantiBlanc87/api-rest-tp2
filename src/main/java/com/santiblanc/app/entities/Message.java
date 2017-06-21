package com.santiblanc.app.entities;

import javax.persistence.*;
import java.sql.Timestamp;

/**
 * Created by Blade-Razer on 6/5/2017.
 */
@Entity
@Table(name = "messages")
public class Message implements Comparable<Message> {
    //Atributos
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @OneToOne
    private User sender;

    @OneToOne
    private User receiver;

    @Column(name = "date", columnDefinition = "datetime")
    private Timestamp date;
    private String subject;
    private String msg;
    private boolean erasedBySender;
    private boolean erasedByReceiver;

    //Inicializacion de Booleanos
    {
        this.setErasedBySender(false);
        this.setErasedByReceiver(false);
    }

    //Constructor
    public Message() {
    }

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

    public void setErasedBySender(boolean erasedBySender) {
        this.erasedBySender = erasedBySender;
    }

    public void setErasedByReceiver(boolean erasedByReceiver) {
        this.erasedByReceiver = erasedByReceiver;
    }

    @Override
    public int compareTo(Message o) {
        return getDate().compareTo(o.getDate());
    }
}
