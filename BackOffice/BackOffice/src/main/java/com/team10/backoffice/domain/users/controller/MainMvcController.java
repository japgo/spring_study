package com.team10.backoffice.domain.users.controller;

import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Log4j2
@Controller
public class MainMvcController {

    @GetMapping("/login")
    public String login() {
        return "login";
    }
    @GetMapping("/")
    public String mainPage() {
        return "index";
    }
    @GetMapping("/board")
    public String boardPage() {
        return "board";
    }
    @GetMapping("/write")
    public String write() {
        return "writepage";
    }
    @GetMapping("/detail")
    public String detail() {
        return "detail";
    }
    @GetMapping("/signup")
    public String signup() {
        return "signup";
    }
    @GetMapping("/userinfo")
    public String userinfo() {
        return "userinfo";
    }

}
