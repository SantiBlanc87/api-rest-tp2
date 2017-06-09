package com.santiblanc.app.converter;

import com.santiblanc.app.entities.User;
import com.santiblanc.app.response.UserWrapper;

/**
 * Created by Blade-Razer on 6/8/2017.
 */
public class UserConverter {
    //Constructor
    public UserConverter(){}

    //Metodos
    public UserWrapper convert (User user){
        UserWrapper u = new UserWrapper();
        u.setNombre(user.getNombre());
        u.setApellido(user.getApellido());
        u.setEmail(user.getEmail());
        return u;
    }
}
