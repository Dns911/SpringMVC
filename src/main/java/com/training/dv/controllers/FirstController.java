package com.training.dv.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;



public class FirstController {

    @GetMapping("/hello")
    public String helloPage() {
        return "first/hello";
    }

    @GetMapping("/goodbye")
    public String goodByePage(@RequestParam(value = "name", required = false, defaultValue = "") String name,
                              @RequestParam(value = "surname", required = false, defaultValue = "") String surname,
                              Model model) {
        System.out.println("name: " + name);
        model.addAttribute("msg", "GoodBye " + name + " " + surname + "!");
        return "first/goodbye";
    }

    @GetMapping("/calc")
    public String calc(@RequestParam(value = "a", required = false, defaultValue = "0") int aI,
                       @RequestParam(value = "b", required = false, defaultValue = "0") int bI,
                       @RequestParam(value = "action", required = false, defaultValue = "add") String action,
                       Model model) {
        String res;
        switch (action) {
            case "mult":
                res = String.valueOf(aI * bI);
                break;
            case "add":
                res = String.valueOf(aI + bI);
                break;
            case "subtr":
                res = String.valueOf(aI - bI);
                break;
            case "div":
                res = String.format("%.3f", ((double) aI / bI));
                break;
            default:
                res = "action fail";
                break;
        }
        model.addAttribute("msg", res);

        return "first/calc";
    }
}
