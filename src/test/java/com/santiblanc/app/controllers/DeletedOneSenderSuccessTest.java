package com.santiblanc.app.controllers;

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
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;

import org.springframework.web.context.WebApplicationContext;

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
public class DeletedOneSenderSuccessTest {

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

        this.userDAO.deleteAll();

        this.sessionData.removeSession(this.sessionId);
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
}
