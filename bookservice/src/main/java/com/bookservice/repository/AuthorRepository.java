package com.bookservice.repository;

import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import com.bookservice.entity.Author;

@Repository
public interface AuthorRepository extends JpaRepository<Author, Long> {
	Optional<Author> findByAuthorname(String authorname);
	Boolean existsByAuthorname(String authorname);
	Boolean existsByAuthoremail(String email);
}
