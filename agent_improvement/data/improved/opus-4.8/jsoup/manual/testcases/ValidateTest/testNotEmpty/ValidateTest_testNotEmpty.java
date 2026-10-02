package org.jsoup.helper;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

/**
 * Tests {@link Validate#notEmpty(String)}, which accepts non-empty strings
 * and rejects empty or null strings with a {@link ValidationException}.
 */
// keeps tests for ensureNotNull
@SuppressWarnings("deprecation")
public class ValidateTest_testNotEmpty {

    private static final String EMPTY_STRING_MESSAGE = "String must not be empty";

    @Test
    public void acceptsNonEmptyString() {
        // A non-empty string passes validation without throwing.
        assertDoesNotThrow(() -> Validate.notEmpty("foo"));
    }

    @Test
    public void rejectsEmptyString() {
        ValidationException exception =
            assertThrows(ValidationException.class, () -> Validate.notEmpty(""));
        assertEquals(EMPTY_STRING_MESSAGE, exception.getMessage());
    }

    @Test
    public void rejectsNullString() {
        ValidationException exception =
            assertThrows(ValidationException.class, () -> Validate.notEmpty(null));
        assertEquals(EMPTY_STRING_MESSAGE, exception.getMessage());
    }
}
