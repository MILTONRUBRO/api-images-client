package br.com.app.customer.infrastructure.persistence;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import br.com.app.customer.domain.model.Customer;

@Repository
public interface CustomerJpaRepository extends JpaRepository<Customer, Long> {
	Optional<Customer> findByEmail(String email);
}
