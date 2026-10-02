package org.jsoup.helper;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

// keeps tests for ensureNotNull
@SuppressWarnings("deprecation")
public class ValidateTest_testIsTrue {

    @Test
    public void testIsTrue() {
        // Test with a true value
        Validate.isTrue(true);
        // Test with a false value
        boolean threw = false;
        try {
            Validate.isTrue(false);
        } catch (ValidationException e) {
            threw = true;
            assertEquals("Must be true", e.getMessage());
        }
        assertTrue(threw);
    }
}
