package com.example.springcourse.services;

import com.example.springcourse.models.Book;
import com.example.springcourse.models.Person;
import com.example.springcourse.repositories.BooksRepository;
import com.example.springcourse.repositories.PeopleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.Optional;

@Service
@Transactional(readOnly = true)
public class BookService {

    private final BooksRepository booksRepository;
    private final PeopleRepository peopleRepository;

    @Autowired
    public BookService(BooksRepository booksRepository, PeopleRepository peopleRepository) {
        this.booksRepository = booksRepository;
        this.peopleRepository = peopleRepository;
    }

    public List<Book> findAll(Optional<Integer> page, Optional<Integer> booksPerPage, Optional<Boolean> sort) {
        if (page.isPresent() && booksPerPage.isPresent()) {
            if (sort.isPresent() && sort.get())
                return booksRepository.findAll(PageRequest.of(page.get(), booksPerPage.get(), Sort.by("year").ascending())).getContent();
            else
                return booksRepository.findAll(PageRequest.of(page.get(), booksPerPage.get())).getContent();
        } else {
            if (sort.isPresent() && sort.get()) {
                return booksRepository.findAll(Sort.by("year"));
            } else {
                return booksRepository.findAll();
            }
        }
    }

    public Book findOne(int id) {
        return booksRepository.findById(id).stream().findAny().orElse(new Book());
    }

    public Book findBySearch(String prefix) {

        return booksRepository.findByNameIgnoreCaseStartingWith(prefix).stream().findAny().orElse(new Book());
    }

    @Transactional
    public void save(Book book) {
        booksRepository.save(book);
    }

    @Transactional
    public void delete(int id) {
        booksRepository.deleteById(id);
    }

    @Transactional
    public List<Book> findByOwner(Person person) {
        List<Book> books = booksRepository.findByOwner(person);
        for (Book book : books) {
            Date date = book.getDate();
            book.setExpired(new Date(new Date().getTime()-1000*60*60*24*10).before(date));
        }

        return books;
    }

    @Transactional
    public List<Person> getOwnerOrAllPeople(Book book) {
        if (book.getOwner() != null) {
            return Collections.singletonList(
                    peopleRepository.getPersonById(book.getOwner().getId()));
        } else {
            return peopleRepository.findAll();
        }
    }

    @Transactional
    public void release(int bookId) {
        booksRepository.findById(bookId).ifPresent(book -> {
            book.setOwner(null);
            booksRepository.save(book);
        });
    }

    @Transactional
    public void assignBook(int bookId, int personId) {
        booksRepository.findById(bookId).ifPresent(book -> {
            Optional<Person> person = peopleRepository.findById(personId).stream().findAny();
            if (person.isPresent()) {
                book.setOwner(person.get());
                book.setDate(new Date());
                person.get().getBooks().add(book);
            }
            booksRepository.save(book);
        });
    }
}
