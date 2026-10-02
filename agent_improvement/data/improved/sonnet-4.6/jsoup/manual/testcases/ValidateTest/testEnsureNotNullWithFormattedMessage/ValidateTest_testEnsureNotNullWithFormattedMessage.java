package org.jsoup.helper;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

// Tests for Validate.ensureNotNull with a formatted message argument
@SuppressWarnings("deprecation")
public class ValidateTest_testEnsureNotNullWithFormattedMessage {

    @Test
    public void testEnsureNotNullWithFormattedMessage() {
        // Non-null input: the same object reference must be returned unchanged
        Object obj = new Object();
        assertSame(obj, Validate.ensureNotNull(obj, "Object must not be null: %s", "additional info"));

        // Null input: a ValidationException must be thrown with the formatted message
        ValidationException thrown = assertThrows(ValidationException.class, () ->
            Validate.ensureNotNull(null, "Object must not be null: %s", "additional info")
        );
        assertEquals("Object must not be null: additional info", thrown.getMessage());
    }
}
