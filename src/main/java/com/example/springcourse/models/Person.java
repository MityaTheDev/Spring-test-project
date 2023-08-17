package com.example.springcourse.models;

import jakarta.persistence.*;


import java.util.Arrays;
import java.util.List;

@Entity
@Table(name = "person")
public class Person {

    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(name = "name")
    private String name;

    @Column(name = "birth_year")
    private int birthYear;

    @OneToMany(mappedBy = "owner")
    private List<Book> books;

    public Person() {
    }

    public Person(String name, int birthYear) {
        this.name = name;
        this.birthYear = birthYear;
    }

    public Person(int id, String name, int birthYear) {
        this.id = id;
        this.name = name;
        this.birthYear = birthYear;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getBirthYear() {
        return birthYear;
    }

    public void setBirthYear(int birthYear) {
        this.birthYear = birthYear;
    }

    public List<Book> getBooks() {
        return books;
    }

    public void setBooks(List<Book> books) {
        this.books = books;
    }

    @Override
    public String toString() {
        return "Person{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", birthYear=" + birthYear +
                ", books: " +
                (books != null ? Arrays.toString(books.toArray()) : "null") +
                '}';
    }
}
