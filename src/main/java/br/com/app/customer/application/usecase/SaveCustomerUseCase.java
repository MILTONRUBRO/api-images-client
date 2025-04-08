package br.com.app.customer.application.usecase;

import java.util.Optional;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import br.com.app.customer.domain.model.Customer;
import br.com.app.customer.domain.model.CustomerRequest;
import br.com.app.customer.domain.model.CustomerResponse;
import br.com.app.customer.infrastructure.persistence.CustomerJpaRepository;
import br.com.app.customer.infrastructure.utils.JwtUtil;
import jakarta.transaction.Transactional;

@Service
public class SaveCustomerUseCase {
	
    @Autowired
    private JwtUtil jwtUtil;

    private final CustomerJpaRepository customerJpaRepository;
    
    private final PasswordEncoder passwordEncoder; 

    public SaveCustomerUseCase(CustomerJpaRepository customerJpaRepository, PasswordEncoder passwordEncoder) {
		this.customerJpaRepository = customerJpaRepository;
		this.passwordEncoder = passwordEncoder;
	}

	@Transactional
    public CustomerResponse save(CustomerRequest request) {
        var customer = new Customer();
        
        BeanUtils.copyProperties(request, customer);
        customer.setSenha(passwordEncoder.encode(request.getSenha()));
        
        Customer savedCustomer = customerJpaRepository.save(customer);
        return new CustomerResponse(savedCustomer.getEmail());
    }
	
	public String generateToken(CustomerRequest request) {
		if(verifyPassword(request)){
			return jwtUtil.generateToken(request.getEmail());
		}
		return "";
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
