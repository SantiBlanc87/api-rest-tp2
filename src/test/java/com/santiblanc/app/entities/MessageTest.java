package com.santiblanc.app.entities;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.sql.Timestamp;
import java.util.Date;

/**
 * Created by Blade-Razer on 6/21/2017.
 */
public class MessageTest {

    private User u,r;

    private Message m1,m2;

    @Before
    public void setup(){
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
        this.m1 = new Message();
        this.m1.setDate(timestamp);
        this.m1.setSubject("TestSubject");
        this.m1.setMsg("TestMsg");
        this.m1.setSender(u);
        this.m1.setReceiver(r);
        this.m1.setErasedByReceiver(false);
        this.m1.setErasedBySender(false);

        Timestamp timestamp2 = new java.sql.Timestamp(date.getTime());
        this.m2 = new Message();
        this.m2.setDate(timestamp2);
        this.m2.setSubject("TestSubject2");
        this.m2.setMsg("TestMsg2");
        this.m2.setSender(u);
        this.m2.setReceiver(r);
        this.m2.setErasedByReceiver(false);
        this.m2.setErasedBySender(false);
    }

    @After
    public void tearDown() {
        this.u = null;
        this.r = null;
        this.m1 = null;
        this.m2 = null;
    }

    @Test
    public void testCompareToSuccess(){
        Assert.assertEquals(0.00,m2.compareTo(m1),0.00);
    }
}
