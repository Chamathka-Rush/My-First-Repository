package com.example.demo.dao;

import com.example.demo.model.Person;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository("fakeDao")
public class FakePersonDataAccessService implements PersonDao {

    //List of persons
    private static List<Person> DB = new ArrayList<>();

    //Implement the method - insertPerson()
    @Override
    public int insertPerson(UUID id, Person person) {
        DB.add(new Person(id, person.getName()));
        return 1;
    }

    //Implement the method - selectAllPeople()
    @Override
    public List<Person> selectAllPeople() {
        return DB;
    }

    //Implement the method - selectPersonById() using Java streams
    @Override
    public Optional<Person> selectPersonById(UUID id) {
        return DB.stream()
                .filter(person -> person.getId().equals(id))
                .findFirst();
    }

    //Implement the method - deletePersonById()
    @Override
    public int deletePersonById(UUID id) {
        Optional<Person> personSelected = selectPersonById(id);
        if (!personSelected.isPresent()) {
            return 0;
        }
        DB.remove(personSelected.get());
        return 1; //We know that we deleted this person when 1 is returned.
    }


    //Implement the method - updatePersonById()
    @Override
    public int updatePersonById(UUID id, Person update) {
        return selectPersonById(id)
        .map(person -> {
            int indexOfPersonToUpdate = DB.indexOf(person);
            if(indexOfPersonToUpdate >= 0){
                DB.set(indexOfPersonToUpdate, new Person(id, update.getName()));
                return 1;
            }
            return 0;
        })
                .orElse(0);
    }
}
