package com.springsecurity.demo.Domain.Person.Controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@RequestMapping(value = "/api/v1/person")
public class PersonController {

    //private final IPersonService personService;

    /*@PostMapping
    public void createPerson(@RequestBody Person person){
        personService.createPerson(person);
    }*/

    @PostMapping
    public String Bienvenidos(){
        return "Bienvenidos";
    }

    @GetMapping
    public String welcome(){
        return "Welcome Spring Security";
    }
}