package com.santiblanc.app.entities;

import javax.persistence.*;

/**
 * Created by Blade-Razer on 6/5/2017.
 */
@Entity
@Table(name = "users")
public class Users {
    //Atributos
    @Id
    @GeneratedValue(strategy= GenerationType.AUTO)
    private Long id;

    private String nombre;
    private String apellido;
    private String direccion;
    private String telefono;
    private String ciudad;
    private String pais;
    private String provincia;
    private String pass;
    private String email;

    @OneToMany
    private Messages msg;
}
