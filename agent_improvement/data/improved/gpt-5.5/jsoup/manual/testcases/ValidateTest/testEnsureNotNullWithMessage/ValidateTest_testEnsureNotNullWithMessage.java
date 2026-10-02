package org.jsoup.helper;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertSame;
import static org.junit.jupiter.api.Assertions.assertThrows;

@SuppressWarnings("deprecation")
public class ValidateTest_testEnsureNotNullWithMessage {
    private static final String NON_NULL_MESSAGE = "Object must not be null";
    private static final String NULL_MESSAGE = "Custom error message";

    @Test
    public void testEnsureNotNullWithMessage() {
        Object value = new Object();

        assertSame(value, Validate.ensureNotNull(value, NON_NULL_MESSAGE));

        ValidationException thrown = assertThrows(
            ValidationException.class,
            () -> Validate.ensureNotNull(null, NULL_MESSAGE)
        );
        assertEquals(NULL_MESSAGE, thrown.getMessage());
    }
}
