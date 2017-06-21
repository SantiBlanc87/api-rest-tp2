package com.santiblanc.app.util;

import com.santiblanc.app.entities.User;
import org.apache.log4j.Logger;
import org.joda.time.DateTime;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Set;
import java.util.UUID;

/**
 * Created by pablo on 01/11/16.
 */
@Service
public class SessionData {

    final static Logger logger = Logger.getLogger(SessionData.class);
    HashMap<String, AuthenticationData> sessionData;

    @Value("${session.expiration}")
    int expirationTime;


    public SessionData() {
        this.sessionData = new HashMap<String, AuthenticationData>();
    }

    public String addSession(User user) throws NonExistingUserException {

        String sessionId = UUID.randomUUID().toString();
        AuthenticationData aData = new AuthenticationData();
        if (user.getDeleted() == false) {
            aData.setUser(user);
            aData.setLastAction(new DateTime());
            this.sessionData.put(sessionId, aData);
            return sessionId;
        } else {
            throw new NonExistingUserException("El Usuario solicitado no existe.");
        }

    }

    public void removeSession(String sessionId) {
        sessionData.remove(sessionId);
    }

    public AuthenticationData getSession(String sessionId) {
        AuthenticationData aData = this.sessionData.get(sessionId);
        if (aData != null) {
            return aData;
        } else {
            return null;
        }
    }

    @Scheduled(fixedRate = 5000)
    public void checkSessions() {
        System.out.println("Checking sessions");
        Set<String> sessionsId = this.sessionData.keySet();
        for (String sessionId : sessionsId) {
            AuthenticationData aData = this.sessionData.get(sessionId);
            if (aData.getLastAction().plusSeconds(expirationTime).isBefore(System.currentTimeMillis())) {
                System.out.println("Deleting sessionId = " + sessionId);
                this.sessionData.remove(sessionId);
            }
        }
    }

}
