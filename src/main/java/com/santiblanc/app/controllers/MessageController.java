package com.santiblanc.app.controllers;

import com.google.common.collect.Lists;
import com.santiblanc.app.converter.MessageConverter;
import com.santiblanc.app.entities.Message;
import com.santiblanc.app.entities.User;
import com.santiblanc.app.persistence.MessageDAO;
import com.santiblanc.app.persistence.UserDAO;
import com.santiblanc.app.request.MessageRequest;
import com.santiblanc.app.response.ErrorWrapper;
import com.santiblanc.app.response.MessageWrapper;
import com.santiblanc.app.util.SessionData;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
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
    MessageConverter messageConverter;
    @Autowired
    MessageDAO messageDAO;
    @Autowired
    UserDAO userDAO;

    //Metodos
    //Envio un email
    @RequestMapping(value = "/messages", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity sendMsg(@RequestBody MessageRequest request, @RequestHeader("email") String email) {
        try {
            User u = userDAO.findByEmail(email);
            User receiver = userDAO.findByEmail(request.getReceiver());
            Date date = new java.util.Date();
            Timestamp timestamp = new java.sql.Timestamp(date.getTime());
            if (u != null && receiver != null) {
                Message m = new Message();
                m.setSender(u);
                m.setReceiver(receiver);
                m.setDate(timestamp);
                m.setSubject(request.getSubject());
                m.setMsg(request.getMsg());
                messageDAO.save(m);
                return new ResponseEntity(HttpStatus.CREATED);
            } else {
                throw new NonValidMessageException("Error al enviar el mensaje al destinataro.");
            }
        } catch (NonValidMessageException e) {
            return new ResponseEntity(new ErrorWrapper(e.getMessage()), HttpStatus.NOT_FOUND);
        } catch (Exception e) {
            return new ResponseEntity(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    //Borro uno o más emails
    @RequestMapping(value = "/messages/{emailIds}", method = RequestMethod.DELETE)
    public ResponseEntity deleteMsg(@PathVariable("emailIds") List<Long> ids, @RequestHeader("email") String email) {
        try {
            User u = userDAO.findByEmail(email);
            List<Message> listBorrar = new ArrayList<Message>();
            List<Message> listInexistente = new ArrayList<Message>();
            for (Long id : ids) {
                Message m = messageDAO.findOne(id);
                if (m.getSender().equals(u) || m.getReceiver().equals(u)) {
                    listBorrar.add(m);
                } else {
                    listInexistente.add(m);
                }
            }

            if (!listBorrar.isEmpty() && listInexistente.isEmpty()) {
                for (Message msg : listBorrar) {
                    if (msg.getReceiver().equals(u)) {

                        msg.setErasedByReceiver(true);
                        messageDAO.save(msg);

                    } else if (msg.getSender().equals(u)) {

                        msg.setErasedBySender(true);
                        messageDAO.save(msg);

                    }
                }
                return new ResponseEntity(HttpStatus.OK);
            } else {
                throw new NonValidMessageException("Uno o más de los Emails no corresponden al usuario en sesión.");
            }
        } catch (NonValidMessageException e) {
            return new ResponseEntity(new ErrorWrapper(e.getMessage()), HttpStatus.FORBIDDEN);
        } catch (Exception e) {
            return new ResponseEntity(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    //Mostrar emails recibidos de usuario
    @RequestMapping(value = "/messages/inbox", method = RequestMethod.GET)
    public @ResponseBody ResponseEntity<List<MessageWrapper>> getInbox(@RequestHeader("email") String email) {
        try {
            User u = userDAO.findByEmail(email);
            Iterable<Message> list = messageDAO.findByReceiverAndErasedByReceiver(u, false);
            List<Message> result = Lists.newArrayList(list);
            Collections.sort(result, Collections.reverseOrder());
            if (result.size() > 0) {
                return new ResponseEntity<List<MessageWrapper>>(this.convertList(result), HttpStatus.OK);
            } else {
                return new ResponseEntity<List<MessageWrapper>>(HttpStatus.NO_CONTENT);
            }
        } catch (Exception e) {
            return new ResponseEntity(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    //Mostrar emails enviados de usuario
    @RequestMapping(value = "/messages/outbox", method = RequestMethod.GET)
    public @ResponseBody ResponseEntity<List<MessageWrapper>> getOutbox(@RequestHeader("email") String email) {
        try {
            User u = userDAO.findByEmail(email);
            Iterable<Message> list = messageDAO.findBySenderAndErasedBySender(u, false);
            List<Message> result = Lists.newArrayList(list);
            Collections.sort(result, Collections.reverseOrder());
            if (result.size() > 0) {
                return new ResponseEntity<List<MessageWrapper>>(this.convertList(result), HttpStatus.OK);
            } else {
                return new ResponseEntity<List<MessageWrapper>>(HttpStatus.NO_CONTENT);
            }
        } catch (Exception e) {
            return new ResponseEntity(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    //Mostrar emails borrados
    @RequestMapping(value = "/messages/deleted", method = RequestMethod.GET)
    public @ResponseBody ResponseEntity<List<MessageWrapper>> getDeleted(@RequestHeader("email") String email) {
        try {
            User u = userDAO.findByEmail(email);
            Iterable<Message> list = messageDAO.findBySenderAndErasedBySenderOrReceiverAndErasedByReceiver(u, true, u, true);
            List<Message> result = Lists.newArrayList(list);
            Collections.sort(result, Collections.reverseOrder());
            if (result.size() > 0) {
                return new ResponseEntity<List<MessageWrapper>>(this.convertList(result), HttpStatus.OK);
            } else {
                return new ResponseEntity<List<MessageWrapper>>(HttpStatus.NO_CONTENT);
            }
        } catch (Exception e) {
            return new ResponseEntity(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    //Metodos adicionales
    private List<MessageWrapper> convertList(List<Message> messages) {
        List<MessageWrapper> userWrapperList = new ArrayList<MessageWrapper>();
        for (Message m : messages) {
            userWrapperList.add(messageConverter.convert(m));
        }
        return userWrapperList;
    }
}
