package com.santiblanc.app.converter;

import com.santiblanc.app.entities.Message;
import com.santiblanc.app.response.MessageWrapper;
import org.joda.time.DateTime;

/**
 * Created by Blade-Razer on 6/8/2017.
 */
public class MessageConverter {
    //Constructor
    public MessageConverter() {
    }

    //Metodos
    public MessageWrapper convert(Message message) {
        MessageWrapper m = new MessageWrapper();
        m.setId(message.getId());
        m.setSender(message.getSender().getEmail());
        m.setReceiver(message.getReceiver().getEmail());
        DateTime date = new DateTime(message.getDate());
        m.setDate(date);
        m.setSubject(message.getSubject());
        m.setMsg(message.getMsg());
        m.setFrom(message.getSender().getNombre() + " " + message.getSender().getApellido());
        return m;
    }
}
