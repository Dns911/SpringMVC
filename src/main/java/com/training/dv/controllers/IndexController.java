package com.training.dv.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;



public class IndexController {
    @GetMapping("")
    public String index(){
        return "people/index";
    }
}
