package org.jsoup.helper;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class ValidateTest_testNotEmptyWithMessage {
    private static final String CUSTOM_ERROR_MESSAGE = "Custom error message";

    @Test
    public void testNotEmptyWithMessage() {
        assertDoesNotThrow(() -> Validate.notEmpty("foo", CUSTOM_ERROR_MESSAGE));

        assertNotEmptyRejects("", CUSTOM_ERROR_MESSAGE);
        assertNotEmptyRejects(null, CUSTOM_ERROR_MESSAGE);
    }

    private static void assertNotEmptyRejects(String input, String expectedMessage) {
        ValidationException exception = assertThrows(
            ValidationException.class,
            () -> Validate.notEmpty(input, expectedMessage)
        );
        assertEquals(expectedMessage, exception.getMessage());
    }
}
