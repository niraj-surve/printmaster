package com.printmaster.app.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.printmaster.app.model.PrintRequest;

public interface PrintRequestRepository extends JpaRepository<PrintRequest, Long> {

	PrintRequest findByTransactionId(String transactionId);
}
