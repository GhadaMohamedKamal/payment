package com.example.payment.repo;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.transaction.annotation.Transactional;


import com.example.payment.model.Merchant;
public interface MerchantRepository extends JpaRepository<Merchant, Long> {
	@Transactional
	List<Merchant> findByActiveTrue();
	
	
	
}
