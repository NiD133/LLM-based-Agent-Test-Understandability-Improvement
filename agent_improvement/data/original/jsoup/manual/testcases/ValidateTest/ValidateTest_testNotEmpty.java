package org.jsoup.helper;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

// keeps tests for ensureNotNull
@SuppressWarnings("deprecation")
public class ValidateTest_testNotEmpty {

    @Test
    public void testNotEmpty() {
        // Test with a non-empty string
        String str = "foo";
        Validate.notEmpty(str);
        // Test with an empty string
        boolean threw = false;
        try {
            Validate.notEmpty("");
        } catch (ValidationException e) {
            threw = true;
            assertEquals("String must not be empty", e.getMessage());
        }
        assertTrue(threw);
        // Test with a null string
        threw = false;
        try {
            Validate.notEmpty(null);
        } catch (ValidationException e) {
            threw = true;
            assertEquals("String must not be empty", e.getMessage());
        }
        assertTrue(threw);
    }
}
