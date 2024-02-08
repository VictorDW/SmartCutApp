package com.springsecurity.practica.Person.Service;

import com.springsecurity.practica.Person.Entity.Person;
import com.springsecurity.practica.Person.Repository.PersonRepository;
import org.springframework.stereotype.Service;

import lombok.NonNull;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class PersonService implements IPersonService {

    private final PersonRepository personRepository;

    @Override
    public void createPerson(@NonNull Person person) {
        personRepository.save(person);
    }
}
