package com.santiblanc.app.response;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * Created by Blade-Razer on 6/12/2017.
 */
public class LoginWrapper {
    //Atributos
    @JsonProperty
    String sessionId;

    //Constructores
    public LoginWrapper(){}

    public LoginWrapper(String id){
        this.setSessionId(id);
    }

    //Getters y Setters

    public String getSessionId() {
        return sessionId;
    }

    public void setSessionId(String sessionId) {
        this.sessionId = sessionId;
    }
}
