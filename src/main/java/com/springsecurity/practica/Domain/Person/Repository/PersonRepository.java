package com.springsecurity.practica.Domain.Person.Repository;

import com.springsecurity.practica.Domain.Person.Entity.Person;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PersonRepository extends JpaRepository<Person,Integer> {

}
