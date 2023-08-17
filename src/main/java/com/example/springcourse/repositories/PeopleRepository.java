package com.example.springcourse.repositories;

import com.example.springcourse.models.Person;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface PeopleRepository extends JpaRepository<Person, Integer> {

    @Query("select p from Person p join Book b on p.id = :id")
    Person getPerson(@Param("id") int id);

    Person getPersonById(int id);
}
