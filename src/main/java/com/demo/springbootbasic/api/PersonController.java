package com.demo.springbootbasic.api;

import com.demo.springbootbasic.model.Person;
import com.demo.springbootbasic.service.PersonService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RequestMapping("api/v1/person")
@RestController
public class PersonController {
    private final PersonService service;

    @Autowired
    public PersonController(PersonService service) {
        this.service = service;
    }

    @PostMapping
    public void addPerson(@RequestBody Person person){
        service.addPerson(person);
    }

    @GetMapping
    public List<Person> getAllPeople(){
        return service.getAllPeople();
    }

    @GetMapping(path = "{id}")
    public Person getPersonById(@PathVariable("id") UUID id){
        return service.getPersonById(id).orElse(null);
    }
}
