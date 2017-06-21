package com.santiblanc.app.response;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * Created by Blade-Razer on 6/20/2017.
 */
public class ErrorWrapper {
    //Atributos
    @JsonProperty("ERROR")
    String error;

    public ErrorWrapper(String s) {
        this.setError(s);
    }

    //Getters y Setters
    public String getError() {
        return error;
    }

    public void setError(String error) {
        this.error = error;
    }
}
