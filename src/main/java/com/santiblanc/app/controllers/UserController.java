package com.santiblanc.app.controllers;

import com.google.common.collect.Lists;
import com.santiblanc.app.converter.UserConverter;
import com.santiblanc.app.entities.User;
import com.santiblanc.app.persistence.UserDAO;
import com.santiblanc.app.request.UserRequest;
import com.santiblanc.app.response.ErrorWrapper;
import com.santiblanc.app.response.UserWrapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Blade-Razer on 6/8/2017.
 */
@Controller
@RequestMapping(
        value = "/users",
        produces = MediaType.APPLICATION_JSON_VALUE
)
public class UserController {
    //Autowires
    @Autowired
    UserConverter userConverter;
    @Autowired
    UserDAO userDAO;

    //Metodos
    //Agregar Usuario
    @RequestMapping(value = "", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity addUser(@RequestBody UserRequest request) {
        try {
            User u = new User();
            u.setNombre(request.getNombre());
            u.setApellido(request.getApellido());
            u.setDireccion(request.getDire());
            u.setTelefono(request.getTel());
            u.setCiudad(request.getCiudad());
            u.setPais(request.getPais());
            u.setProvincia(request.getProvincia());
            u.setPass(request.getPass());
            u.setEmail(request.getEmail());
            u.setRecoveryEmail(request.getRecoveryEmail());
            userDAO.save(u);
            return new ResponseEntity(HttpStatus.CREATED);
        } catch (DataIntegrityViolationException e){
            return new ResponseEntity(new ErrorWrapper("El email solicitado ya ha sido utilizado"),HttpStatus.CONFLICT);
        } catch (Exception e) {
            return new ResponseEntity(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    //Borrar Usuario
    @RequestMapping(value = "", method = RequestMethod.DELETE)
    public ResponseEntity deleteUser(@RequestParam("email") String em) {
        try {
            User u = userDAO.findByEmail(em);
            if (u != null) {
                u.setDeleted(true);
                userDAO.save(u);
                return new ResponseEntity(HttpStatus.OK);
            } else {
                return new ResponseEntity(HttpStatus.NOT_FOUND);
            }
        } catch (Exception e) {
            return new ResponseEntity(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    //Traer lista de todos los usuarios
    @RequestMapping(value = "", method = RequestMethod.GET)
    public
    @ResponseBody
    ResponseEntity<List<UserWrapper>> getAll() {
        try {
            Iterable<User> list = userDAO.findAll();
            List<User> result = Lists.newArrayList(list);
            if (result.size() > 0) {
                return new ResponseEntity<List<UserWrapper>>(this.convertList(result), HttpStatus.OK);
            } else {
                return new ResponseEntity<List<UserWrapper>>(HttpStatus.NO_CONTENT);
            }
        } catch (Exception e) {
            return new ResponseEntity(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    //Traer Usuario por similitud
    @RequestMapping(value = "/{nombre}", method = RequestMethod.GET)
    public
    @ResponseBody
    ResponseEntity<List<UserWrapper>> getLike(@PathVariable("nombre") String nom) {
        try {
            List<User> result = userDAO.findByNombre(nom);
            if (result.size() > 0) {
                return new ResponseEntity<List<UserWrapper>>(this.convertList(result), HttpStatus.OK);
            } else {
                return new ResponseEntity<List<UserWrapper>>(HttpStatus.NO_CONTENT);
            }
        } catch (Exception e) {
            return new ResponseEntity(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    //Metodos adicionales
    private List<UserWrapper> convertList(List<User> users) {
        List<UserWrapper> userWrapperList = new ArrayList<UserWrapper>();
        for (User u : users) {
            userWrapperList.add(userConverter.convert(u));
        }
        return userWrapperList;
    }

}
