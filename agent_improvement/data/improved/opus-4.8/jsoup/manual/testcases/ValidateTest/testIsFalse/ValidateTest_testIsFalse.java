package org.jsoup.helper;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

// keeps tests for ensureNotNull
@SuppressWarnings("deprecation")
public class ValidateTest_testIsFalse {

    @Test
    public void testIsFalse() {
        // A false value passes validation and must not throw.
        assertDoesNotThrow(() -> Validate.isFalse(false));

        // A true value fails validation and must throw with the default message.
        ValidationException thrown = assertThrows(
            ValidationException.class,
            () -> Validate.isFalse(true)
        );
        assertEquals("Must be false", thrown.getMessage());
    }
}
