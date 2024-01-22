package com.springsecurity.practica.Person;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@RequestMapping(value = "/person")
public class PersonController {

    private final IPersonService personService;
    
    @PostMapping
    public void createPerson(@RequestBody Person person){
        personService.createPerson(person);
    }
}
