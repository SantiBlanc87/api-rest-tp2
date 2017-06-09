package com.santiblanc.app.request;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * Created by Blade-Razer on 6/7/2017.
 */
public class UserRequest {
    //Propiedades
    @JsonProperty("nombre")
    String nombre;
    @JsonProperty("apellido")
    String apellido;
    @JsonProperty("direccion")
    String dire;
    @JsonProperty("telefono")
    String tel;
    @JsonProperty("ciudad")
    String ciudad;
    @JsonProperty("pais")
    String pais;
    @JsonProperty("provincia")
    String provincia;
    @JsonProperty("password")
    String pass;
    @JsonProperty("email")
    String email;

    //Constructor
    public UserRequest(){}

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

    public String getDire() {
        return dire;
    }

    public void setDire(String dire) {
        this.dire = dire;
    }

    public String getTel() {
        return tel;
    }

    public void setTel(String tel) {
        this.tel = tel;
    }

    public String getCiudad() {
        return ciudad;
    }

    public void setCiudad(String ciudad) {
        this.ciudad = ciudad;
    }

    public String getPais() {
        return pais;
    }

    public void setPais(String pais) {
        this.pais = pais;
    }

    public String getProvincia() {
        return provincia;
    }

    public void setProvincia(String provincia) {
        this.provincia = provincia;
    }

    public String getPass() {
        return pass;
    }

    public void setPass(String pass) {
        this.pass = pass;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
}
