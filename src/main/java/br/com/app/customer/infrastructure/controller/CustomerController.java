package br.com.app.customer.infrastructure.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.com.app.customer.application.usecase.SaveCustomerUseCase;
import br.com.app.customer.domain.model.AuthResponse;
import br.com.app.customer.domain.model.Customer;
import br.com.app.customer.domain.model.CustomerRequest;
import br.com.app.customer.domain.model.CustomerResponse;

@RestController
@RequestMapping("/customers")
public class CustomerController {
	
    private final SaveCustomerUseCase saveCustomerUseCase;

    @Autowired
    public CustomerController(SaveCustomerUseCase saveCustomerUseCase) {
        this.saveCustomerUseCase = saveCustomerUseCase;
    }

    @PostMapping
    public ResponseEntity<CustomerResponse> save(@RequestBody CustomerRequest request) {
        return ResponseEntity.ok(saveCustomerUseCase.save(request));
    }
    
    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody CustomerRequest request) {
    	String token = saveCustomerUseCase.generateToken(request);
    	
    	if(token.isBlank()) {
    		return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
    	}
    	return ResponseEntity.ok(new AuthResponse(token));
    	
    }

}
