package service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.payment.model.Customer;
import com.example.payment.model.Merchant;
import com.example.payment.repo.CustomerRepository;
import com.example.payment.repo.MerchantRepository;

@Service
public class MerchantService {
    @Autowired
    private MerchantRepository merchantRepo;

    @Autowired
    private CustomerRepository customerRepo;

    public Merchant getMerchantById(Long id) {
        return merchantRepo.findById(id).orElse(null);
    }

    public List<Merchant> getAllActiveMerchants() {
        return merchantRepo.findByActiveTrue();
    }

    public List<Customer> getCustomersWithoutTransactions() {
        return customerRepo.findCustomersWithoutTransactions();
    }
}
