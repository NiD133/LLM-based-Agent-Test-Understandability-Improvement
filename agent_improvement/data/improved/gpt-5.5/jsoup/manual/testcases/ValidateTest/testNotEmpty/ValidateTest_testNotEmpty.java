package org.jsoup.helper;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class ValidateTest_testNotEmpty {

    @Test
    public void testNotEmpty() {
        Validate.notEmpty("foo");

        assertNotEmptyRejects("", "String must not be empty");
        assertNotEmptyRejects(null, "String must not be empty");
    }

    private static void assertNotEmptyRejects(String input, String expectedMessage) {
        ValidationException exception = assertThrows(
            ValidationException.class,
            () -> Validate.notEmpty(input)
        );
        assertEquals(expectedMessage, exception.getMessage());
    }
}
