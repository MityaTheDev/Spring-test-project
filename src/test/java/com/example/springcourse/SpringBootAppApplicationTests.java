package com.example.springcourse;

import com.example.springcourse.controllers.PersonController;
import com.example.springcourse.models.Person;
import com.example.springcourse.repositories.BooksRepository;
import com.example.springcourse.repositories.PeopleRepository;
import com.example.springcourse.services.BookService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;


import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringRunner;

//@WebMvcTest(PersonController.class)
//@RunWith(SpringRunner.class)
@SpringBootTest(classes = SpringBootAppApplication.class)
//@TestPropertySource(locations = "classpath:application.properties")
class SpringBootAppApplicationTests {

	@Autowired
	private PeopleRepository peopleRepository;

	@Test
	void contextLoads() {
	}

	@Test
	public void findPersonByIdShouldReturnPersonNamedBobbie() {
		Assertions.assertEquals(
				peopleRepository.findById(1).stream().findAny().orElse(new Person()).getName(),
				"Bobbie");
	}

}
