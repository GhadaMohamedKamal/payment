package com.example.payment.repo;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.example.payment.model.Customer;

public interface CustomerRepository extends JpaRepository<Customer, Long> {


    @Query("SELECT c FROM Customer c WHERE c.transactions IS EMPTY")
    List<Customer> findCustomersWithoutTransactions();
}
