package com.example.springcourse.repositories;

import com.example.springcourse.models.Book;
import com.example.springcourse.models.Person;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface BooksRepository extends JpaRepository<Book, Integer> {
    List<Book> findByOwner(Person person);

    Optional<Book> findByNameIgnoreCaseStartingWith(String prefix);

}
