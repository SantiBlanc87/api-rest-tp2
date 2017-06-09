package com.santiblanc.app.converter;

import com.santiblanc.app.entities.Message;
import com.santiblanc.app.response.MessageWrapper;

/**
 * Created by Blade-Razer on 6/8/2017.
 */
public class MessageConverter {
    //Constructor
    public MessageConverter(){}

    //Metodos
    public MessageWrapper convert (Message message){
        MessageWrapper m = new MessageWrapper();
        m.setSender(message.getSender().getEmail());
        m.setReceiver(message.getReceiver().getEmail());
        m.setDate(message.getDate());
        m.setSubject(message.getSubject());
        m.setMsg(message.getMsg());
        return m;
    }
}
