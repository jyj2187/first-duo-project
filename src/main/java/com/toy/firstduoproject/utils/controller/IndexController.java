package com.toy.firstduoproject.utils.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class IndexController {
    @GetMapping("/")
    public String main() {
        return "redirect:/posts";
    }
}
