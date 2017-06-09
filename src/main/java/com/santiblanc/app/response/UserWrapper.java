package com.santiblanc.app.response;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * Created by Blade-Razer on 6/7/2017.
 */
public class UserWrapper {
    //Propiedades
    @JsonProperty("nombre")
    String nombre;
    @JsonProperty("apellido")
    String apellido;
    @JsonProperty("email")
    String email;

    //Constructor
    public UserWrapper(){}

    //Getters and Setters

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getApellido() {
        return apellido;
    }

    public void setApellido(String apellido) {
        this.apellido = apellido;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
}
