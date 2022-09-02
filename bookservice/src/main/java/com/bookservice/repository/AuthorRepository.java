package com.bookservice.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.stereotype.Repository;

import com.bookservice.entity.Author;
import com.bookservice.entity.Book;


@Repository
public interface AuthorRepository extends JpaRepository<Author, Long> {
	Optional<Author> findByAuthorname(String authorname);

	Boolean existsByAuthorname(String authorname);

	Boolean existsByAuthoremail(String email);
}
