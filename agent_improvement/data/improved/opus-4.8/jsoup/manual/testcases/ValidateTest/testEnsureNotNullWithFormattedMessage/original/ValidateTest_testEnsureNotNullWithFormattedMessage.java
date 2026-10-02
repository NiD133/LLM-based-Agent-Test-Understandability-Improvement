package org.jsoup.helper;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

// keeps tests for ensureNotNull
@SuppressWarnings("deprecation")
public class ValidateTest_testEnsureNotNullWithFormattedMessage {

    @Test
    public void testEnsureNotNullWithFormattedMessage() {
        // Test with a non-null object
        Object obj = new Object();
        assertSame(obj, Validate.ensureNotNull(obj, "Object must not be null: %s", "additional info"));
        // Test with a null object
        boolean threw = false;
        try {
            Validate.ensureNotNull(null, "Object must not be null: %s", "additional info");
        } catch (ValidationException e) {
            threw = true;
            assertEquals("Object must not be null: additional info", e.getMessage());
        }
        assertTrue(threw);
    }
}
