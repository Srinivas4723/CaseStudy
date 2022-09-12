package com.bookservice.service;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.bookservice.entity.Author;
import com.bookservice.entity.Book;
import com.bookservice.repository.BookRepository;

@Service
public class BookService {
	@Autowired
	BookRepository repository;
	public void save(@Valid Book user) {
		repository.save(user);
	}
	public Iterable<Book> getUser() {
		return repository.findAll();
	}

}
