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
import java.util.Optional;

@Controller
@RequestMapping("/books")
public class BookController {

    private final BookService bookService;
    private final PersonService personService;

    @Autowired
    public BookController(BookService bookService, PersonService personService) {
        this.bookService = bookService;
        this.personService = personService;
    }

    @GetMapping()
    public String index(@RequestParam(name = "page", required = false) Optional<Integer> page,
                        @RequestParam(name = "books_per_page", required = false) Optional<Integer> booksPerPage,
                        @RequestParam(name = "sort_by_year", required = false) Optional<Boolean> sort,
                        Model model) {

        model.addAttribute("books", bookService.findAll(page, booksPerPage, sort));

        return "books/index";
    }

    @GetMapping("/search")
    public String search(@RequestParam(name = "search", required = false) Optional<String> prefix, Model model) {
        if (prefix.isPresent()) {
            Book book = bookService.findBySearch(prefix.get());
            model.addAttribute("book", book);
        }
        return "books/search";
    }

    @GetMapping("/{id}")
    public String showOne(@PathVariable("id") int id, Model model) {
        Book book = bookService.findOne(id);
        List<Person> people = bookService.getOwnerOrAllPeople(book);

        model.addAttribute("book", book);
        model.addAttribute("people", people);
        model.addAttribute("personIdHolder", new Person());

        return "books/show";
    }

    @PostMapping("/{id}")
    public String assignBook(@PathVariable("id") int id,
                             @ModelAttribute("personIdHolder") Person person) {
        bookService.assignBook(id, person.getId());

        return "redirect:/books/" + id;
    }

    @PatchMapping("/{id}")
    public String releaseBook(@PathVariable("id") int id) {
        bookService.release(id);

        return "redirect:/books/" + id;
    }

    @GetMapping("/new")
    public String newBookPage(@ModelAttribute Book book) {

        return "books/new";
    }

    @PostMapping("/new")
    public String createNewBook(@ModelAttribute Book book) {
        bookService.save(book);

        return "redirect:/books";
    }

    @GetMapping("/{id}/edit")
    public String editPage(@PathVariable("id") int id, Model model) {
        model.addAttribute("book", bookService.findOne(id));

        return "books/edit";
    }

    @PatchMapping("{id}/edit")
    public String edit(@ModelAttribute("book") Book updatedBook) {
        bookService.save(updatedBook);

        return "redirect:/books";
    }

    @DeleteMapping("/{id}")
    public String delete(@PathVariable("id") int id) {
        bookService.delete(id);

        return "redirect:/books";
    }
}
