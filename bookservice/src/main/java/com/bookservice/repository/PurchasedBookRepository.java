package com.bookservice.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import com.bookservice.entity.PurchasedBooks;

@Repository
public interface PurchasedBookRepository extends JpaRepository<PurchasedBooks, Long> {
	List<PurchasedBooks> findAllByReaderemail(String readeremail);
	Optional<PurchasedBooks> findByPaymentid(String paymentid);
	Optional<PurchasedBooks> findByReaderemailAndBookid(String readeremail, Long bookid);
}
