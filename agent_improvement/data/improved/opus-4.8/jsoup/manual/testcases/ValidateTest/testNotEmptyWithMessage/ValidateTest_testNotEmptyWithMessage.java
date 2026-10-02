package org.jsoup.helper;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

// keeps tests for ensureNotNull
@SuppressWarnings("deprecation")
public class ValidateTest_testNotEmptyWithMessage {

    private static final String CUSTOM_MESSAGE = "Custom error message";

    @Test
    public void testNotEmptyWithMessage() {
        // A non-empty string passes validation without throwing.
        assertDoesNotThrow(() -> Validate.notEmpty("foo", CUSTOM_MESSAGE));

        // An empty string fails, surfacing the supplied custom message.
        ValidationException emptyFailure = assertThrows(ValidationException.class,
            () -> Validate.notEmpty("", CUSTOM_MESSAGE));
        assertEquals(CUSTOM_MESSAGE, emptyFailure.getMessage());

        // A null string fails, surfacing the supplied custom message.
        ValidationException nullFailure = assertThrows(ValidationException.class,
            () -> Validate.notEmpty(null, CUSTOM_MESSAGE));
        assertEquals(CUSTOM_MESSAGE, nullFailure.getMessage());
    }
}
