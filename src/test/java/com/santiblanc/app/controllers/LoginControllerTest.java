package com.santiblanc.app.controllers;

import com.santiblanc.app.App;
import com.santiblanc.app.entities.User;
import com.santiblanc.app.persistence.UserDAO;
import com.santiblanc.app.util.SessionData;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;
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

import static java.util.Arrays.asList;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
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
public class LoginControllerTest {

    private MockMvc mockMvc;

    @Autowired
    private WebApplicationContext webApplicationContext;

    @Autowired
    private UserDAO userDAO;

    @Autowired
    private SessionData sessionData;

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

        this.userDAO.save(u);
    }

    @After
    public void setupAfter() throws Exception {
        this.userDAO.deleteAll();
    }

    @Test
    public void testLoginSuccess() throws Exception {
        mockMvc.perform(post("/login")
                .contentType(MediaType.APPLICATION_FORM_URLENCODED)
                .content(EntityUtils.toString(new UrlEncodedFormEntity(asList(
                        new BasicNameValuePair("email", "TestEmail"),
                        new BasicNameValuePair("pass", "TestPassword")
                )))))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8));
    }

    @Test
    public void testLoginFailure() throws Exception {
        mockMvc.perform(post("/login")
                .contentType(MediaType.APPLICATION_FORM_URLENCODED)
                .content(EntityUtils.toString(new UrlEncodedFormEntity(asList(
                        new BasicNameValuePair("email", "test"),
                        new BasicNameValuePair("pass", "test")
                )))))
                .andExpect(status().isForbidden());
    }

    @Test
    public void testDeletedLoginFailure() throws Exception {
        this.u.setDeleted(true);
        this.userDAO.save(u);
        mockMvc.perform(post("/login")
                .contentType(MediaType.APPLICATION_FORM_URLENCODED)
                .content(EntityUtils.toString(new UrlEncodedFormEntity(asList(
                        new BasicNameValuePair("email", "TestEmail"),
                        new BasicNameValuePair("pass", "TestPassword")
                )))))
                .andExpect(status().isNotFound());
    }

    @Test
    public void testLogout() throws Exception {
        String sessionid = this.sessionData.addSession(this.u);

        mockMvc.perform(get("/logout")
                .header("sessionid", sessionid))
                .andExpect(status().isAccepted());
    }

}
