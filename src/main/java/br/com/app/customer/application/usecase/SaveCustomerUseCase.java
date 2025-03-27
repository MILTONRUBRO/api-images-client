package br.com.app.customer.application.usecase;

import java.util.Optional;

import org.springframework.beans.BeanUtils;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import br.com.app.customer.domain.model.Customer;
import br.com.app.customer.domain.model.CustomerRequest;
import br.com.app.customer.infrastructure.persistence.CustomerJpaRepository;
import jakarta.transaction.Transactional;

@Service
public class SaveCustomerUseCase {

    private final CustomerJpaRepository customerJpaRepository;
    
    private final PasswordEncoder passwordEncoder; 

    public SaveCustomerUseCase(CustomerJpaRepository customerJpaRepository, PasswordEncoder passwordEncoder) {
		this.customerJpaRepository = customerJpaRepository;
		this.passwordEncoder = passwordEncoder;
	}

	@Transactional
    public Customer save(CustomerRequest request) {
        var customer = new Customer();
        
        BeanUtils.copyProperties(request, customer);
        customer.setSenha(passwordEncoder.encode(request.getSenha()));
        
        return customerJpaRepository.save(customer);
    }
	
    public boolean verifyPassword(CustomerRequest request) {
        Optional<Customer> optionalCustomer = customerJpaRepository.findByEmail(request.getEmail());
        if (optionalCustomer.isPresent()) {
            Customer customer = optionalCustomer.get();
            return passwordEncoder.matches(request.getSenha(), customer.getSenha());
        }
        return false;
    }

}
