package com.example.springcourse.controllers;

import com.example.springcourse.models.Book;
import com.example.springcourse.models.Person;
import com.example.springcourse.services.BookService;
import com.example.springcourse.services.PersonService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/people")
public class PersonController {

    private final PersonService peopleService;
    private final BookService bookService;

    @Autowired
    public PersonController(PersonService peopleService, BookService bookService) {
        this.peopleService = peopleService;
        this.bookService = bookService;
    }

    @GetMapping
    public String findAll(Model model) {
        List<Person> people = peopleService.findAll();
        model.addAttribute("people", people);

        return "people/index";
    }

    @GetMapping("/{id}")
    public String findOne(Model model, @PathVariable int id) {
        Person person = peopleService.findOne(id);
        List<Book> books = bookService.findByOwner(person);

        model.addAttribute("person", person);
        model.addAttribute("books", books);

        return "people/show";
    }

    @GetMapping("/new")
    public String createPage(@ModelAttribute("person") Person person) {

        return "people/new";
    }

    @PostMapping("/new")
    public String create(@ModelAttribute("person") Person person) {
        peopleService.save(person);

        return "redirect:/people";
    }

    @GetMapping("/{id}/edit")
    public String editPage(@PathVariable("id") int id, Model model) {
        model.addAttribute(peopleService.findOne(id));

        return "people/edit";
    }

    @PatchMapping("/{id}/edit")
    public String edit(@ModelAttribute("person") Person updatedPerson) {
        System.out.println(updatedPerson);
        peopleService.save(updatedPerson);

        return "redirect:/people";
    }

    @DeleteMapping("/{id}")
    public String delete(@PathVariable("id") int id) {
        peopleService.delete(id);

        return "redirect:/people";
    }
}
