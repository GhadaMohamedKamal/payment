package com.example.payment.repo;
import org.springframework.data.jpa.repository.JpaRepository;

import com.example.payment.model.PaymentTransaction;
public interface PaymentTransactionRepository extends JpaRepository<PaymentTransaction, Long> { /* Additional custom methods */ }

