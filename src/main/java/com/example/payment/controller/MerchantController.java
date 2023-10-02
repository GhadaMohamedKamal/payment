package com.example.payment.controller;


import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import com.example.payment.model.Customer;
import com.example.payment.model.Merchant;

import com.example.payment.service.MerchantService;

@RestController

public class MerchantController {
    @Autowired
    private MerchantService merchantService;
    
    public MerchantController(MerchantService merchantService) {
        this.merchantService = merchantService;
       
    }
    @GetMapping("/merchants/{id}")
    public  ResponseEntity<Merchant> getMerchantById(@PathVariable("id")  Long id) {
         
    	Merchant merchant = merchantService.getMerchantById(id);;
        return new ResponseEntity<>(merchant, HttpStatus.OK);
    }

    @GetMapping("/merchants/active")
    public ResponseEntity<List<Merchant>> getAllActiveMerchants (){
    	//return null;
    	List<Merchant> merchants =merchantService.getAllActiveMerchants() ;
    	return new ResponseEntity<>(merchants, HttpStatus.OK);
    }
   
    @GetMapping("/customers/no-transactions")
    public ResponseEntity<List<Customer>> getCustomersWithoutTransactions(){
    	List<Customer>  customers =merchantService.getCustomersWithoutTransactions() ;
    	return new ResponseEntity<>(customers, HttpStatus.OK);
        
    }
}