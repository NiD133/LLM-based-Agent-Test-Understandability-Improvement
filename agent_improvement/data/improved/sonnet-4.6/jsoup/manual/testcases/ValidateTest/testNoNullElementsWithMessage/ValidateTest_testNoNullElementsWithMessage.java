package org.jsoup.helper;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

// Tests for Validate.noNullElements(Object[], String)
public class ValidateTest_testNoNullElementsWithMessage {

    @Test
    public void testNoNullElementsWithMessage() {
        // Array with no null elements should pass validation without throwing
        Object[] validArray = { new Object(), new Object() };
        assertDoesNotThrow(() -> Validate.noNullElements(validArray, "Custom error message"));

        // Array containing a null element should throw ValidationException with the custom message
        Object[] arrayWithNull = { new Object(), null };
        ValidationException thrown = assertThrows(
            ValidationException.class,
            () -> Validate.noNullElements(arrayWithNull, "Custom error message")
        );
        assertEquals("Custom error message", thrown.getMessage());
    }
}
