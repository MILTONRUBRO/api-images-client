package br.com.app.customer.application.usecase;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;
import static org.mockito.Mockito.*;
import org.junit.jupiter.api.BeforeEach;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.security.crypto.password.PasswordEncoder;
import br.com.app.customer.domain.model.Customer;
import br.com.app.customer.domain.model.CustomerRequest;
import br.com.app.customer.domain.model.CustomerResponse;
import br.com.app.customer.infrastructure.persistence.CustomerJpaRepository;
import java.util.Optional;

class SaveCustomerUseCaseTest {
	@Mock
	private CustomerJpaRepository customerJpaRepository;

	@Mock
	private PasswordEncoder passwordEncoder;

	@InjectMocks
	private SaveCustomerUseCase saveCustomerUseCase;

	@BeforeEach
	void setUp() {
		MockitoAnnotations.openMocks(this);
	}

	@Test
	void testSave() {
		CustomerRequest request = new CustomerRequest();
		request.setEmail("test@example.com");
		request.setSenha("password");

		Customer customer = new Customer();
		when(passwordEncoder.encode(request.getSenha())).thenReturn("encodedPassword");
		when(customerJpaRepository.save(any(Customer.class))).thenReturn(customer);

		CustomerResponse savedCustomer = saveCustomerUseCase.save(request);

		assertNotNull(savedCustomer);
		verify(customerJpaRepository, times(1)).save(any(Customer.class));
		verify(passwordEncoder, times(1)).encode(request.getSenha());
	}

	@Test
	void testVerifyPassword() {
		CustomerRequest request = new CustomerRequest();
		request.setEmail("test@example.com");
		request.setSenha("password");

		Customer customer = new Customer();
		customer.setSenha("encodedPassword");

		when(customerJpaRepository.findByEmail(request.getEmail())).thenReturn(Optional.of(customer));
		when(passwordEncoder.matches(request.getSenha(), customer.getSenha())).thenReturn(true);

		boolean result = saveCustomerUseCase.verifyPassword(request);

		assertTrue(result);
		verify(customerJpaRepository, times(1)).findByEmail(request.getEmail());
		verify(passwordEncoder, times(1)).matches(request.getSenha(), customer.getSenha());
	}

	@Test
	void testVerifyPasswordWithInvalidEmail() {
		CustomerRequest request = new CustomerRequest();
		request.setEmail("invalid@example.com");
		request.setSenha("password");

		when(customerJpaRepository.findByEmail(request.getEmail())).thenReturn(Optional.empty());

		boolean result = saveCustomerUseCase.verifyPassword(request);

		assertFalse(result);
		verify(customerJpaRepository, times(1)).findByEmail(request.getEmail());
		verify(passwordEncoder, times(0)).matches(anyString(), anyString());
	}

}
