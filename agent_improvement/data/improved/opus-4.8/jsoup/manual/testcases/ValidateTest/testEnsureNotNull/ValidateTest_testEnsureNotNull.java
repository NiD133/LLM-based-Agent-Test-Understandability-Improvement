package org.jsoup.helper;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertSame;
import static org.junit.jupiter.api.Assertions.assertThrows;

/**
 * Tests for {@link Validate#ensureNotNull(Object)}, which returns the given
 * object when it is non-null and otherwise throws a {@link ValidationException}.
 */
@SuppressWarnings("deprecation")
public class ValidateTest_testEnsureNotNull {

    @Test
    public void testEnsureNotNull() {
        // A non-null object is returned unchanged (same instance).
        Object input = new Object();
        assertSame(input, Validate.ensureNotNull(input));

        // A null object triggers a ValidationException with a descriptive message.
        ValidationException thrown = assertThrows(
                ValidationException.class,
                () -> Validate.ensureNotNull(null));
        assertEquals("Object must not be null", thrown.getMessage());
    }
}
