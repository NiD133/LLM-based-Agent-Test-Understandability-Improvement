package org.jsoup.helper;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

// Tests for Validate.ensureNotNull(Object, String)
@SuppressWarnings("deprecation")
public class ValidateTest_testEnsureNotNullWithMessage {

    @Test
    public void ensureNotNull_withNonNullObject_returnsSameObject() {
        Object obj = new Object();
        assertSame(obj, Validate.ensureNotNull(obj, "Object must not be null"));
    }

    @Test
    public void ensureNotNull_withNullObject_throwsValidationExceptionWithCustomMessage() {
        ValidationException exception = assertThrows(
            ValidationException.class,
            () -> Validate.ensureNotNull(null, "Custom error message")
        );
        assertEquals("Custom error message", exception.getMessage());
    }
}
