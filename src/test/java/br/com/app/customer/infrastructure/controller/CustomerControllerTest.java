package br.com.app.customer.infrastructure.controller;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import br.com.app.customer.application.usecase.SaveCustomerUseCase;
import br.com.app.customer.domain.model.AuthResponse;
import br.com.app.customer.domain.model.CustomerRequest;
import br.com.app.customer.domain.model.CustomerResponse;

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
		CustomerResponse customerResponse = new CustomerResponse();
		when(saveCustomerUseCase.save(request)).thenReturn(customerResponse);

		ResponseEntity<CustomerResponse> response = customerController.save(request);

		assertEquals(ResponseEntity.ok(customerResponse), response);
		verify(saveCustomerUseCase, times(1)).save(request);
	}
	@Test
	void testLoginSuccess() {
		CustomerRequest request = new CustomerRequest();
		String token = "validToken";
		when(saveCustomerUseCase.generateToken(request)).thenReturn(token);

		ResponseEntity<?> response = customerController.login(request);

		assertEquals(ResponseEntity.ok(new AuthResponse(token)), response);
		verify(saveCustomerUseCase, times(1)).generateToken(request);
	}

	@Test
	void testLoginUnauthorized() {
		CustomerRequest request = new CustomerRequest();
		when(saveCustomerUseCase.generateToken(request)).thenReturn("");

		ResponseEntity<?> response = customerController.login(request);

		assertEquals(HttpStatus.UNAUTHORIZED, response.getStatusCode());
		verify(saveCustomerUseCase, times(1)).generateToken(request);
	}



}
