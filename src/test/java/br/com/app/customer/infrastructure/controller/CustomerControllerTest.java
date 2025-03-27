package br.com.app.customer.infrastructure.controller;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;
import static org.mockito.Mockito.*;
import org.junit.jupiter.api.BeforeEach;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.ResponseEntity;
import br.com.app.customer.application.usecase.SaveCustomerUseCase;
import br.com.app.customer.domain.model.Customer;
import br.com.app.customer.domain.model.CustomerRequest;

class CustomerControllerTest {

	@Mock
	private SaveCustomerUseCase saveCustomerUseCase;

	@InjectMocks
	private CustomerController customerController;

	@BeforeEach
	void setUp() {
		MockitoAnnotations.openMocks(this);
	}

	@Test
	void testSave() {
		CustomerRequest request = new CustomerRequest();
		Customer customer = new Customer();
		when(saveCustomerUseCase.save(request)).thenReturn(customer);

		ResponseEntity<Customer> response = customerController.save(request);

		assertEquals(ResponseEntity.ok(customer), response);
		verify(saveCustomerUseCase, times(1)).save(request);
	}

	@Test
	void testVerificarSenha() {
		CustomerRequest request = new CustomerRequest();
		when(saveCustomerUseCase.verifyPassword(request)).thenReturn(true);

		boolean result = customerController.verificarSenha(request);

		assertTrue(result);
		verify(saveCustomerUseCase, times(1)).verifyPassword(request);
	}

}
