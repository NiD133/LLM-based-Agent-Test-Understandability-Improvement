package org.jsoup.helper;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertSame;
import static org.junit.jupiter.api.Assertions.assertThrows;

/**
 * Tests for {@link Validate#ensureNotNull(Object, String, Object...)}, the overload
 * that takes a custom message.
 */
@SuppressWarnings("deprecation")
public class ValidateTest_testEnsureNotNullWithMessage {

    @Test
    public void returnsSameObjectWhenNotNull() {
        Object input = new Object();

        Object result = Validate.ensureNotNull(input, "Object must not be null");

        assertSame(input, result);
    }

    @Test
    public void throwsWithCustomMessageWhenNull() {
        ValidationException thrown = assertThrows(ValidationException.class,
            () -> Validate.ensureNotNull(null, "Custom error message"));

        assertEquals("Custom error message", thrown.getMessage());
    }
}
