package br.com.app.customer.infrastructure.utils;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.BeforeEach;

class JwtUtilTest {

	private JwtUtil jwtUtil;

	@BeforeEach
	void setUp() {
		jwtUtil = new JwtUtil();
	}

	@Test
	void testGenerateToken() {
		String username = "testUser";
		String token = jwtUtil.generateToken(username);

		assertNotNull(token);
		assertFalse(token.isEmpty());
	}

	@Test
	void testExtractUsername() {
		String username = "testUser";
		String token = jwtUtil.generateToken(username);

		String extractedUsername = jwtUtil.extractUsername(token);

		assertEquals(username, extractedUsername);
	}

	@Test
	void testValidateToken_ValidToken() {
		String username = "testUser";
		String token = jwtUtil.generateToken(username);

		boolean isValid = jwtUtil.validateToken(token);

		assertTrue(isValid);
	}

	@Test
	void testValidateToken_InvalidToken() {
		String invalidToken = "invalidToken";

		boolean isValid = jwtUtil.validateToken(invalidToken);

		assertFalse(isValid);
	}

}
