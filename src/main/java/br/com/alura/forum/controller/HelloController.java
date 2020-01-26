package br.com.alura.forum.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class HelloController {

    @RequestMapping(value = "/", method = RequestMethod.POST)
    @ResponseBody // Notação utilizada somente quando não há JSP

    public String hello(){
        return "Hello World";
    }
}
