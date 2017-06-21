package com.santiblanc.app.util;

import com.santiblanc.app.entities.User;
import org.joda.time.DateTime;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import java.util.UUID;

/**
 * Created by Blade-Razer on 6/21/2017.
 */
public class AuthenticationDataTest {

    private SessionData test;

    private AuthenticationData authenticationData;

    private User u;

    private String sessionId;

    private DateTime date;


    @Before
    public void setup() {

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

        this.date = new DateTime();

        this.authenticationData = new AuthenticationData();
        this.authenticationData.setUser(u);
        this.authenticationData.setLastAction(date);

        this.sessionId = UUID.randomUUID().toString();

    }

    @After
    public void tearDown() {
        this.authenticationData = null;
        this.u = null;
        this.sessionId=null;
        this.test = null;
    }

    @Test
    public void testAuthenticationDataSuccess() {
        test = new SessionData();
        test.sessionData.put(sessionId,authenticationData);
        Assert.assertEquals(authenticationData,test.getSession(sessionId));
    }

    @Test
    public void testAuthenticationDataFailure() {
        test = new SessionData();
        test.sessionData.put(sessionId,null);
        Assert.assertEquals(null,test.getSession(sessionId));
    }

    @Test
    public void testAuthDataGetLastAction() {
        Assert.assertEquals(this.date,this.authenticationData.getLastAction());
    }

    @Test
    public void testAuthDataGetUser() {
        Assert.assertEquals(this.u,this.authenticationData.getUser());
    }
}