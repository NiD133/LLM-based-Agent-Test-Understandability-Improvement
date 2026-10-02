package org.jsoup.helper;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

// Tests for Validate.ensureNotNull: verifies the method returns non-null objects and throws on null input.
@SuppressWarnings("deprecation")
public class ValidateTest_testEnsureNotNull {

    @Test
    public void ensureNotNull_returnsTheSameObjectWhenGivenNonNull() {
        Object obj = new Object();
        assertSame(obj, Validate.ensureNotNull(obj));
    }

    @Test
    public void ensureNotNull_throwsValidationExceptionWithDefaultMessageWhenGivenNull() {
        ValidationException ex = assertThrows(
            ValidationException.class,
            () -> Validate.ensureNotNull(null)
        );
        assertEquals("Object must not be null", ex.getMessage());
    }
}
