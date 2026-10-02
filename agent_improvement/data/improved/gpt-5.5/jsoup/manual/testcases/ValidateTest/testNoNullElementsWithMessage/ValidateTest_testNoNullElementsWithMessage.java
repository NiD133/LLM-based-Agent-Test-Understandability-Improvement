package org.jsoup.helper;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

// keeps tests for ensureNotNull
@SuppressWarnings("deprecation")
public class ValidateTest_testNoNullElementsWithMessage {
    private static final String CUSTOM_ERROR_MESSAGE = "Custom error message";

    @Test
    public void testNoNullElementsWithMessage() {
        Object[] arrayWithoutNullElements = { new Object(), new Object() };

        assertDoesNotThrow(() ->
            Validate.noNullElements(arrayWithoutNullElements, CUSTOM_ERROR_MESSAGE)
        );

        ValidationException thrown = assertThrows(ValidationException.class, () ->
            Validate.noNullElements(new Object[] { new Object(), null }, CUSTOM_ERROR_MESSAGE)
        );
        assertEquals(CUSTOM_ERROR_MESSAGE, thrown.getMessage());
    }
}
