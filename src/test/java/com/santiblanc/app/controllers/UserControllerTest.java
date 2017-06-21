package com.santiblanc.app.controllers;

import com.google.common.base.Charsets;
import com.google.common.io.Resources;
import com.santiblanc.app.App;
import com.santiblanc.app.entities.User;
import com.santiblanc.app.persistence.UserDAO;
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
public class UserControllerTest {

    private MockMvc mockMvc;

    @Autowired
    private WebApplicationContext webApplicationContext;

    @Autowired
    private UserDAO userDAO;

    private User u;


    @Before
    public void setup() throws Exception {
        this.mockMvc = webAppContextSetup(webApplicationContext).build();

        this.u = new User();
        this.u.setNombre("TestNombre");
        this.u.setApellido("TestApellido");
        this.u.setDireccion("TestDireccion");
        this.u.setTelefono("TestTelefono");
        this.u.setCiudad("TestCiudad");
        this.u.setProvincia("TestProvincia");
        this.u.setPais("TestPais");
        this.u.setEmail("TestEmail");
        this.u.setPass("TestPassword");
        this.u.setRecoveryEmail("TestRecuperacion");
        this.u.setDeleted(false);
    }

    @After
    public void setupAfter() throws Exception {
        this.userDAO.deleteAll();
    }

    @Test
    public void testGetAllSuccess() throws Exception {
        this.userDAO.save(u);
        mockMvc.perform(
                get("/users"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8));
    }

    @Test
    public void testGetAllNone() throws Exception {
        mockMvc.perform(
                get("/users"))
                .andExpect(status().isNoContent());
    }

    @Test
    public void testGetOneSuccess() throws Exception {
        this.userDAO.save(u);
        mockMvc.perform(
                get("/users/{nombre}","TestNombre"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8));
    }

    @Test
    public void testGetOneNone() throws Exception {
        mockMvc.perform(
                get("/users/{nombre}","TestNombre"))
                .andExpect(status().isNoContent());
    }

    @Test
    public void testAddUserSuccess() throws Exception {
        URL url = Resources.getResource("user.json");
        String json = Resources.toString(url, Charsets.UTF_8);

        mockMvc.perform(
                post("/users")
                        .contentType(MediaType.APPLICATION_JSON_UTF8)
                        .content(json))
                .andExpect(status().isCreated());
    }

    @Test
    public void testAddUserAlreadyMade() throws Exception {
        this.userDAO.save(u);
        URL url = Resources.getResource("user.json");
        String json = Resources.toString(url, Charsets.UTF_8);

        mockMvc.perform(
                post("/users")
                        .contentType(MediaType.APPLICATION_JSON_UTF8)
                        .content(json))
                .andExpect(status().isInternalServerError());
    }

    @Test
    public void testDeleteSuccess() throws Exception {
        this.userDAO.save(u);

        mockMvc.perform(
                delete("/users")
                        .param("email", "TestEmail"))
                .andExpect(status().isOk());
    }

    @Test
    public void testDeleteFailure() throws Exception {
        this.u.setDeleted(true);
        mockMvc.perform(
                delete("/users")
                        .param("email", "TestEmail"))
                .andExpect(status().isNotFound());
    }

}

