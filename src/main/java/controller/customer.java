package controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.example.payment.model.Customer;
import com.example.payment.model.Merchant;

import service.MerchantService;



@RestController

public class customer {
   
  

   
    @RequestMapping(method=RequestMethod.GET,path="/test")
    public String getCustomersWithoutTransactions(){
    	
    	return "ghada";
        
    }
}
