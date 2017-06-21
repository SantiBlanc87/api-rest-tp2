package com.santiblanc.app.controllers;

import com.santiblanc.app.entities.User;
import com.santiblanc.app.persistence.UserDAO;
import com.santiblanc.app.response.ErrorWrapper;
import com.santiblanc.app.response.LoginWrapper;
import com.santiblanc.app.util.NonExistingUserException;
import com.santiblanc.app.util.SessionData;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

/**
 * Created by Blade-Razer on 6/8/2017.
 */
@Controller
@RequestMapping(
        value = "/",
        produces = MediaType.APPLICATION_JSON_VALUE
)
public class LoginController {
    //Autowires
    @Autowired
    SessionData sessionData;
    @Autowired
    UserDAO userDAO;

    //Metodos
    @RequestMapping(value = "/login", method = RequestMethod.POST)
    public
    @ResponseBody
    ResponseEntity getByEmail(@RequestParam("email") String email, @RequestParam("pass") String pwd) {
        User u = userDAO.findByEmailAndPass(email, pwd);
        if (u != null) {
            try {
                String sessionId = sessionData.addSession(u);
                return new ResponseEntity<LoginWrapper>(new LoginWrapper(sessionId), HttpStatus.OK);
            } catch (NonExistingUserException e) {
                return new ResponseEntity<ErrorWrapper>(new ErrorWrapper(e.getMessage()), HttpStatus.NOT_FOUND);
            }
        }
        return new ResponseEntity(HttpStatus.FORBIDDEN);
    }


    @RequestMapping(value = "/logout")
    public
    @ResponseBody
    ResponseEntity getById(@RequestHeader("sessionid") String sessionId) {
        sessionData.removeSession(sessionId);
        return new ResponseEntity(HttpStatus.ACCEPTED);
    }
}
