package org.jsoup.helper;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class ValidateTest_testNotEmpty {

    @Test
    public void notEmpty_acceptsNonEmptyString() {
        // Should not throw for a valid non-empty string
        assertDoesNotThrow(() -> Validate.notEmpty("foo"));
    }

    @Test
    public void notEmpty_throwsForEmptyString() {
        ValidationException ex = assertThrows(
            ValidationException.class,
            () -> Validate.notEmpty("")
        );
        assertEquals("String must not be empty", ex.getMessage());
    }

    @Test
    public void notEmpty_throwsForNullString() {
        ValidationException ex = assertThrows(
            ValidationException.class,
            () -> Validate.notEmpty(null)
        );
        assertEquals("String must not be empty", ex.getMessage());
    }
}
