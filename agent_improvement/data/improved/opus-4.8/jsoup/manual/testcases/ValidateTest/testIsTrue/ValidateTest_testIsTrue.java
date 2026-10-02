package org.jsoup.helper;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

// keeps tests for ensureNotNull
@SuppressWarnings("deprecation")
public class ValidateTest_testIsTrue {

    @Test
    public void isTrue_acceptsTrue_andRejectsFalse() {
        // A true value passes validation without throwing.
        assertDoesNotThrow(() -> Validate.isTrue(true));

        // A false value fails validation with the default message.
        ValidationException thrown = assertThrows(ValidationException.class,
                () -> Validate.isTrue(false));
        assertEquals("Must be true", thrown.getMessage());
    }
}
