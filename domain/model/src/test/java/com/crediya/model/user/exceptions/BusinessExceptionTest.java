package com.crediya.model.user.exceptions;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class BusinessExceptionTest {

	@Test
	void shouldCreateExceptionWithMessage() {
		String message = "Business logic error";
		BusinessException exception = new BusinessException(message);

		assertEquals(message, exception.getMessage());
	}

	@Test
	void shouldCreateExceptionWithNullMessage() {
		BusinessException exception = new BusinessException(null);

		assertNull(exception.getMessage());
	}

	@Test
	void shouldBeRuntimeException() {
		BusinessException exception = new BusinessException("test");

		assertTrue(exception instanceof RuntimeException);
	}

	@Test
	void shouldBeThrownAndCaught() {
		String message = "Test business error";

		BusinessException thrown = assertThrows(BusinessException.class, () -> {
			throw new BusinessException(message);
		});

		assertEquals(message, thrown.getMessage());
	}

	@Test
	void shouldHaveProperInheritance() {
		BusinessException exception = new BusinessException("test");

		assertTrue(exception instanceof RuntimeException);
		assertTrue(exception instanceof Exception);
		assertTrue(exception instanceof Throwable);
	}

	@Test
	void shouldAllowChainingWithCause() {
		RuntimeException cause = new RuntimeException("Original cause");
		BusinessException exception = new BusinessException("Business error");
		exception.initCause(cause);

		assertEquals("Business error", exception.getMessage());
		assertEquals(cause, exception.getCause());
	}
}
