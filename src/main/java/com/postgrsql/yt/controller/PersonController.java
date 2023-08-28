package com.postgrsql.yt.controller;

import com.postgrsql.yt.entity.Person;
import com.postgrsql.yt.repository.PersonRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/persons")
public class PersonController {
    @Autowired
    PersonRepository personRepository;

    @GetMapping("")
    public ResponseEntity<List<Person>> getAll(){
        List<Person> persons = personRepository.findAll();
        return ResponseEntity.ok(persons);
    }
    @GetMapping("/{id}")
    public ResponseEntity<Person> getPersonById(@PathVariable Long id) {
        Optional<Person> personOptional = personRepository.findById(id);
        //return personOptional.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
        if (personOptional.isPresent()) {
            return ResponseEntity.ok(personOptional.get());
        } else {
            return ResponseEntity.notFound().build();
        }
    }
    @PostMapping("")
    public ResponseEntity<Person> addPerson(@RequestBody Person person){
        Person savedPerson = personRepository.save(person);
        return ResponseEntity.ok(savedPerson);
    }
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletePerson( @PathVariable Long id){
        Optional<Person> personToDelete = personRepository.findById(id);

        if(personToDelete.isPresent()){
            personRepository.delete(personToDelete.get());
            return ResponseEntity.noContent().build();
        }else{
            return ResponseEntity.notFound().build();
        }
    }
    @PutMapping("/{id}")
    public ResponseEntity<Person> updatePerson(@PathVariable Long id, @RequestBody Person newPerson){
        Optional<Person> personInDB = personRepository.findById(id);

        if(personInDB.isPresent()){
            Person personToUptade = personInDB.get();
            personToUptade.setName(newPerson.getName());

            Person savedPerson = personRepository.save(personToUptade);
            return ResponseEntity.ok(savedPerson);
        }else{
            return ResponseEntity.notFound().build();
        }
    }
}
