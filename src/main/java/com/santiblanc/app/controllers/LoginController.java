package com.santiblanc.app.controllers;

import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * Created by Blade-Razer on 6/8/2017.
 */
@Controller
@RequestMapping(
        value = "/",
        produces = MediaType.APPLICATION_JSON_VALUE
)
public class LoginController {

}
