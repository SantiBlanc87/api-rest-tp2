package com.santiblanc.app.controllers;

import com.google.common.collect.Lists;
import com.santiblanc.app.converter.MessageConverter;
import com.santiblanc.app.entities.Message;
import com.santiblanc.app.persistence.MessageDAO;
import com.santiblanc.app.request.MessageRequest;
import com.santiblanc.app.response.MessageWrapper;
import com.santiblanc.app.util.SessionData;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Blade-Razer on 6/8/2017.
 */
@Controller
@RequestMapping(
        value = "/api",
        produces = MediaType.APPLICATION_JSON_VALUE
)
public class MessageController {
    //Autowired
    @Autowired
    SessionData sessionData;
    @Autowired
    MessageConverter messageConverter;
    @Autowired
    MessageDAO dao;

    //Constructor
    public MessageController(){}

    //Metodos
    //Envio un email
    @RequestMapping(value = "/messages" , method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity sendMsg(@RequestBody MessageRequest request){
        try {
            Message m = new Message();
            m.setSender(request.getSender());
            m.setReceiver(request.getReceiver());
            return new ResponseEntity(HttpStatus.CREATED);
        } catch (Exception e) {
            return new ResponseEntity(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    //Borro un email
    @RequestMapping(value = "/messages/{id}", method = RequestMethod.DELETE)
    public ResponseEntity deleteMsg(@PathVariable("id") Long id){
        try {
            dao.delete(id); //AGREGAR VALIDACION PARA VER QUE ERROR TIRAR, SI BAD REQUEST O NOT FOUND
            return new ResponseEntity(HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    //Mostrar emails recibidos de usuario
    @RequestMapping(value = "/messages/inbox/{id}", method = RequestMethod.GET)
    public @ResponseBody ResponseEntity<List<MessageWrapper>> getInbox(@PathVariable("id") Long id){
        Iterable<Message> list = dao.findByReceiver(id);
        List<Message> result = Lists.newArrayList(list);
        if (result.size()>0) {
            return new ResponseEntity<List<MessageWrapper>>(this.convertList(result), HttpStatus.OK);
        } else {
            return new ResponseEntity<List<MessageWrapper>>(HttpStatus.NO_CONTENT);
        }
    }

    //Mostrar emails enviados de usuario
    @RequestMapping(value = "/messages/outbox/{id}", method = RequestMethod.GET)
    public @ResponseBody ResponseEntity<List<MessageWrapper>> getOutbox(@PathVariable("id") Long id){
        Iterable<Message> list = dao.findBySender(id);
        List<Message> result = Lists.newArrayList(list);
        if (result.size()>0) {
            return new ResponseEntity<List<MessageWrapper>>(this.convertList(result), HttpStatus.OK);
        } else {
            return new ResponseEntity<List<MessageWrapper>>(HttpStatus.NO_CONTENT);
        }
    }

    //Mostrar emails borrados
    @RequestMapping(value = "/messages/deleted/{id}", method = RequestMethod.GET)
    public @ResponseBody ResponseEntity<List<MessageWrapper>> getDeleted(@PathVariable("id") Long id){
        Iterable<Message> list = dao.findByReceiverAndErased(id,true);
        List<Message> result = Lists.newArrayList(list);
        if (result.size()>0) {
            return new ResponseEntity<List<MessageWrapper>>(this.convertList(result), HttpStatus.OK);
        } else {
            return new ResponseEntity<List<MessageWrapper>>(HttpStatus.NO_CONTENT);
        }
    }

    //Metodos adicionales
    private List<MessageWrapper> convertList(List<Message> messages){
        List<MessageWrapper> userWrapperList = new ArrayList<MessageWrapper>();
        for (Message m: messages) {
            userWrapperList.add(messageConverter.convert(m));
        }
        return userWrapperList;
    }
}
