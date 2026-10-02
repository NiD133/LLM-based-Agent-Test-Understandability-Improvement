package org.jsoup.helper;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

// keeps tests for ensureNotNull
@SuppressWarnings("deprecation")
public class ValidateTest_testEnsureNotNull {

    @Test
    public void testEnsureNotNull() {
        // Test with a non-null object
        Object obj = new Object();
        assertSame(obj, Validate.ensureNotNull(obj));
        // Test with a null object
        boolean threw = false;
        try {
            Validate.ensureNotNull(null);
        } catch (ValidationException e) {
            threw = true;
            assertEquals("Object must not be null", e.getMessage());
        }
        assertTrue(threw);
    }
}
