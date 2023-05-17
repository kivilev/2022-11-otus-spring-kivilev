
/*
 * Copyright (c) 2023.
 * Author: Kivilev Denis <kivilev.d@gmail.com>
 */

package org.kivilev.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.security.Principal;

@Controller
public class MainController {
    @GetMapping("/")
    public String main(Model model, Principal principal) {
        String userName = "Anonymous";
        if (principal != null) {
            userName = principal.getName();
        }
        model.addAttribute("greeting", String.format("Hi, %s!", userName));
        return "index";
    }
}
