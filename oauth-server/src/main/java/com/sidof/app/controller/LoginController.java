package com.sidof.app.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

/**
 * <blockquote><pre>
 * Author   : @Dountio
 * LinkedIn : @SidofDountio
 * GitHub   : @SidofDountio
 * Version  : V1.0
 * Email    : sidofdountio406@gmail.com
 * Since    : 12/25/25
 * </blockquote></pre>
 */

@Controller
public class LoginController {

    @GetMapping("/login")
    public String login() {
        return "login";
    }
}
