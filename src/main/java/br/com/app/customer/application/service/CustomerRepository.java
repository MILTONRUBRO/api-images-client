package br.com.app.customer.application.service;

import java.util.Optional;

import br.com.app.customer.domain.model.Customer;

public interface CustomerRepository {
    Optional<Customer> findByEmail(String email);
}
