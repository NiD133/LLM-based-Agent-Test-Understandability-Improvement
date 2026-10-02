package org.jsoup.helper;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

// keeps tests for ensureNotNull
@SuppressWarnings("deprecation")
public class ValidateTest_testNotEmptyWithMessage {

    @Test
    public void testNotEmptyWithMessage() {
        // Test with a non-empty string
        Validate.notEmpty("foo", "Custom error message");
        // Test with an empty string
        boolean threw = false;
        try {
            Validate.notEmpty("", "Custom error message");
        } catch (ValidationException e) {
            threw = true;
            assertEquals("Custom error message", e.getMessage());
        }
        assertTrue(threw);
        // Test with a null string
        threw = false;
        try {
            Validate.notEmpty(null, "Custom error message");
        } catch (ValidationException e) {
            threw = true;
            assertEquals("Custom error message", e.getMessage());
        }
        assertTrue(threw);
    }
}
