package com.santiblanc.app.controllers;

import com.google.common.base.Charsets;
import com.google.common.io.Resources;
import com.santiblanc.app.App;
import com.santiblanc.app.entities.Message;
import com.santiblanc.app.entities.User;
import com.santiblanc.app.persistence.MessageDAO;
import com.santiblanc.app.persistence.UserDAO;
import com.santiblanc.app.util.SessionData;
import org.junit.*;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;

import org.springframework.web.context.WebApplicationContext;

import java.net.URL;
import java.sql.Timestamp;
import java.util.Date;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.setup.MockMvcBuilders.webAppContextSetup;

/**
 * Created by Blade-Razer on 6/20/2017.
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = App.class)
@WebAppConfiguration
@ActiveProfiles("default")
public class MessageControllerTest {

    private MockMvc mockMvc;

    @Autowired
    private WebApplicationContext webApplicationContext;

    @Autowired
    private UserDAO userDAO;

    @Autowired
    private MessageDAO messageDAO;

    @Autowired
    private SessionData sessionData;

    private String sessionId;

    private User u,r;

    private Message m;

    @Before
    public void setup() throws Exception {
        this.mockMvc = webAppContextSetup(webApplicationContext).build();

        this.u = new User();
        this.u.setNombre("TestSenderNombre");
        this.u.setApellido("TestSenderApellido");
        this.u.setDireccion("TestSenderDireccion");
        this.u.setTelefono("TestSenderTelefono");
        this.u.setCiudad("TestSenderCiudad");
        this.u.setProvincia("TestSenderProvincia");
        this.u.setPais("TestSenderPais");
        this.u.setEmail("TestSenderEmail");
        this.u.setPass("TestSenderPassword");
        this.u.setRecoveryEmail("TestSenderRecuperacion");
        this.u.setDeleted(false);

        this.r = new User();
        this.r.setNombre("TestReceiverNombre");
        this.r.setApellido("TestReceiverApellido");
        this.r.setDireccion("TestReceiverDireccion");
        this.r.setTelefono("TestReceiverTelefono");
        this.r.setCiudad("TestReceiverCiudad");
        this.r.setProvincia("TestReceiverProvincia");
        this.r.setPais("TestReceiverPais");
        this.r.setEmail("TestReceiverEmail");
        this.r.setPass("TestReceiverPassword");
        this.r.setRecoveryEmail("TestReceiverRecuperacion");
        this.r.setDeleted(false);

        Date date = new java.util.Date();
        Timestamp timestamp = new java.sql.Timestamp(date.getTime());
        this.m = new Message();
        this.m.setDate(timestamp);
        this.m.setSubject("TestSubject");
        this.m.setMsg("TestMsg");
        this.m.setErasedByReceiver(false);
        this.m.setErasedBySender(false);

        this.sessionId = this.sessionData.addSession(u);
    }

    @After
    public void setupAfter() throws Exception {
        this.messageDAO.deleteAll();
        this.m = null;
        this.userDAO.deleteAll();
        this.u = null;
        this.r = null;
        this.sessionData.removeSession(this.sessionId);
    }

    @Test
    public void testGetInboxSuccess() throws Exception {
        this.userDAO.save(u);
        this.userDAO.save(r);

        this.m.setSender(r);
        this.m.setReceiver(u);

        this.messageDAO.save(m);

        mockMvc.perform(
                get("/api/messages/inbox")
                        .header("sessionId", this.sessionId)
                        .header("email", this.u.getEmail())
        )
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8));
    }

    @Test
    public void testGetInboxNone() throws Exception {
        this.userDAO.save(u);
        this.userDAO.save(r);

        this.m.setSender(r);
        this.m.setReceiver(u);

        mockMvc.perform(
                get("/api/messages/inbox")
                        .header("sessionId", this.sessionId)
                        .header("email", this.u.getEmail())
        )
                .andExpect(status().isNoContent());
    }

    @Test
    public void testGetOutboxSuccess() throws Exception {
        this.userDAO.save(u);
        this.userDAO.save(r);

        this.m.setSender(u);
        this.m.setReceiver(r);

        this.messageDAO.save(m);

        mockMvc.perform(
                get("/api/messages/outbox")
                        .header("sessionId", this.sessionId)
                        .header("email", this.u.getEmail())
        )
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8));
    }

    @Test
    public void testGetOutboxNone() throws Exception {
        this.userDAO.save(u);
        this.userDAO.save(r);

        this.m.setSender(u);
        this.m.setReceiver(r);


        mockMvc.perform(
                get("/api/messages/outbox")
                        .header("sessionId", this.sessionId)
                        .header("email", this.u.getEmail())
        )
                .andExpect(status().isNoContent());
    }

    @Test
    public void testGetDeletedBySenderSuccess() throws Exception {
        this.userDAO.save(u);
        this.userDAO.save(r);

        this.m.setSender(u);
        this.m.setReceiver(r);

        this.m.setErasedBySender(true);

        this.messageDAO.save(m);

        mockMvc.perform(
                get("/api/messages/deleted")
                        .header("sessionId", this.sessionId)
                        .header("email", this.u.getEmail())
        )
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8));
    }

    @Test
    public void testGetDeletedByReceiverSuccess() throws Exception {
        this.userDAO.save(u);
        this.userDAO.save(r);

        this.m.setSender(r);
        this.m.setReceiver(u);

        this.m.setErasedByReceiver(true);

        this.messageDAO.save(m);

        mockMvc.perform(
                get("/api/messages/deleted")
                        .header("sessionId", this.sessionId)
                        .header("email", this.u.getEmail())
        )
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8));
    }

    @Test
    public void testGetDeletedNone() throws Exception {
        this.userDAO.save(u);
        this.userDAO.save(r);

        this.m.setSender(u);
        this.m.setReceiver(r);

        this.messageDAO.save(m);

        mockMvc.perform(
                get("/api/messages/deleted")
                        .header("sessionId", this.sessionId)
                        .header("email", this.u.getEmail())
        )
                .andExpect(status().isNoContent());
    }

    @Test
    public void testSendMsgSuccess() throws Exception {
        this.userDAO.save(u);
        this.userDAO.save(r);

        this.m.setSender(u);
        this.m.setReceiver(r);

        URL url = Resources.getResource("message.json");
        String json = Resources.toString(url, Charsets.UTF_8);


        mockMvc.perform(
                post("/api/messages")
                        .header("sessionId", this.sessionId)
                        .header("email", this.u.getEmail())
                        .contentType(MediaType.APPLICATION_JSON_UTF8)
                        .content(json))
                .andExpect(status().isCreated());
    }

    @Test
    public void testSendMsgFailure() throws Exception {
        this.userDAO.save(u);
        this.r = null;

        this.m.setSender(u);
        this.m.setReceiver(r);

        URL url = Resources.getResource("message.json");
        String json = Resources.toString(url, Charsets.UTF_8);


        mockMvc.perform(
                post("/api/messages")
                        .header("sessionId", this.sessionId)
                        .header("email", this.u.getEmail())
                        .contentType(MediaType.APPLICATION_JSON_UTF8)
                        .content(json))
                .andExpect(status().isNotFound());
    }

    @Test
    public void testDeletedOneReceiverSuccess() throws Exception {
        this.userDAO.save(u);
        this.userDAO.save(r);

        this.m.setSender(r);
        this.m.setReceiver(u);

        this.messageDAO.save(m);

        mockMvc.perform(
                delete("/api/messages/{emailIds}", 1)
                        .header("sessionId", this.sessionId)
                        .header("email", this.u.getEmail())
        )
                .andExpect(status().isOk());

    }

    @Test
    public void testDeletedOneSenderSuccess() throws Exception {
        this.userDAO.save(u);
        this.userDAO.save(r);

        this.m.setSender(u);
        this.m.setReceiver(r);

        this.messageDAO.save(m);

        mockMvc.perform(
                delete("/api/messages/{emailIds}", 1)
                        .header("sessionId", this.sessionId)
                        .header("email", this.u.getEmail())
        )
                .andExpect(status().isInternalServerError());
    }

    @Test
    public void testDeletedMistakeISE() throws Exception {
        this.userDAO.save(u);
        this.userDAO.save(r);

        this.m.setSender(u);
        this.m.setReceiver(r);

        this.messageDAO.save(m);

        mockMvc.perform(
                delete("/api/messages/{emailIds}", 9)
                        .header("sessionId", this.sessionId)
                        .header("email", this.u.getEmail())
        )
                .andExpect(status().isInternalServerError());
    }

    @Test
    public void testDeletedFailure() throws Exception {
        this.userDAO.save(u);
        this.userDAO.save(r);

        this.m.setSender(r);
        this.m.setReceiver(r);

        this.messageDAO.save(m);

        mockMvc.perform(
                delete("/api/messages/{emailIds}", 1)
                        .header("sessionId", this.sessionId)
                        .header("email", this.u.getEmail())
        )
                .andExpect(status().isInternalServerError());
    }

    @Test
    public void testDeletedMultipleSuccess() throws Exception {
        this.userDAO.save(u);
        this.userDAO.save(r);

        this.m.setSender(r);
        this.m.setReceiver(u);

        this.messageDAO.save(m);

        Date date = new java.util.Date();
        Timestamp timestamp = new java.sql.Timestamp(date.getTime());
        Message aux = new Message();
        aux.setSender(u);
        aux.setReceiver(r);
        aux.setDate(timestamp);
        aux.setSubject("TestSubject");
        aux.setMsg("TestMsg");
        aux.setErasedByReceiver(false);
        aux.setErasedBySender(false);
        this.messageDAO.save(aux);

        mockMvc.perform(
                delete("/api/messages/{emailIds}", 1,2)
                        .header("sessionId", this.sessionId)
                        .header("email", this.u.getEmail())
        )
                .andExpect(status().isInternalServerError());
    }

}
