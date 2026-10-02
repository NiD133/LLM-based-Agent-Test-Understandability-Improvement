package org.jsoup.helper;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

// keeps tests for ensureNotNull
@SuppressWarnings("deprecation")
public class ValidateTest_testNoNullElementsWithMessage {

    @Test
    public void testNoNullElementsWithMessage() {
        // Test with an array with no null elements
        Object[] array = { new Object(), new Object() };
        Validate.noNullElements(array, "Custom error message");
        // Test with an array containing a null element
        boolean threw = false;
        try {
            Validate.noNullElements(new Object[] { new Object(), null }, "Custom error message");
        } catch (ValidationException e) {
            threw = true;
            assertEquals("Custom error message", e.getMessage());
        }
        assertTrue(threw);
    }
}
