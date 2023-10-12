package com.example.todolist.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/rotaRota")
public class MinhaPrimeiraController {

    @GetMapping("/funcionou")
    public String primeiraMensagem() {
        return "Funcionou";
    }
}
