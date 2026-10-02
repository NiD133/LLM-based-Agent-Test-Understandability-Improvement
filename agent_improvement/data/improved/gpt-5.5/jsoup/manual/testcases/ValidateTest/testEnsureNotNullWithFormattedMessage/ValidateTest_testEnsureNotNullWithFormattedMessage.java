package org.jsoup.helper;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertSame;
import static org.junit.jupiter.api.Assertions.assertThrows;

// keeps tests for ensureNotNull
@SuppressWarnings("deprecation")
public class ValidateTest_testEnsureNotNullWithFormattedMessage {
    private static final String MESSAGE_TEMPLATE = "Object must not be null: %s";
    private static final String MESSAGE_ARGUMENT = "additional info";
    private static final String FORMATTED_MESSAGE = "Object must not be null: additional info";

    @Test
    public void testEnsureNotNullWithFormattedMessage() {
        Object obj = new Object();

        assertSame(obj, Validate.ensureNotNull(obj, MESSAGE_TEMPLATE, MESSAGE_ARGUMENT));

        ValidationException exception = assertThrows(ValidationException.class, () ->
                Validate.ensureNotNull(null, MESSAGE_TEMPLATE, MESSAGE_ARGUMENT));
        assertEquals(FORMATTED_MESSAGE, exception.getMessage());
    }
}
