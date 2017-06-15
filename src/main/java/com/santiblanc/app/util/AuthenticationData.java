package com.santiblanc.app.util;

import com.santiblanc.app.entities.User;
import org.joda.time.DateTime;


/**
 * Created by pablo on 01/11/16.
 */
public class AuthenticationData {

    private User user;
    private DateTime lastAction;

    public DateTime getLastAction() {
        return lastAction;
    }

    public void setLastAction(DateTime lastAction) {
        this.lastAction = lastAction;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }
}
