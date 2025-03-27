package br.com.app.customer.infrastructure.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.com.app.customer.application.usecase.SaveCustomerUseCase;
import br.com.app.customer.domain.model.Customer;
import br.com.app.customer.domain.model.CustomerRequest;

@RestController
@RequestMapping("/customers")
public class CustomerController {
	
    private final SaveCustomerUseCase saveCustomerUseCase;

    @Autowired
    public CustomerController(SaveCustomerUseCase saveCustomerUseCase) {
        this.saveCustomerUseCase = saveCustomerUseCase;
    }

    @PostMapping
    public ResponseEntity<Customer>  save(@RequestBody CustomerRequest request) {
        return ResponseEntity.ok(saveCustomerUseCase.save(request));
    }
    
    @PostMapping("/login")
    public boolean verificarSenha(@RequestBody CustomerRequest request) {
        return saveCustomerUseCase.verifyPassword(request);
    }

}
