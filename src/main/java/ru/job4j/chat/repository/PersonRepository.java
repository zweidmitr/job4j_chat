package ru.job4j.chat.repository;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import ru.job4j.chat.domain.Person;

import java.util.List;

public interface PersonRepository extends CrudRepository<Person, Integer> {
    List<Person> findAll();

    @Query("select p from  Person p "
            + "where p.login = :login")
    Person findPersonByLogin(String login);

    @Query("select distinct p from Person p "
            + "left join fetch p.messages m "
            + "where m.room.id = (select r.id from Room r where r.name like :name)")
    List<Person> findAllPeopleInRoom(String name);
}
