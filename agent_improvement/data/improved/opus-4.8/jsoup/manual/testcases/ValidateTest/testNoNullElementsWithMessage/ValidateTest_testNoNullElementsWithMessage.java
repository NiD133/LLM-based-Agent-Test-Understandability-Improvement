package org.jsoup.helper;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

/**
 * Tests {@link Validate#noNullElements(Object[], String)}, which validates that an array
 * contains no null elements and reports a caller-supplied message when it does.
 */
@SuppressWarnings("deprecation") // keeps tests for ensureNotNull
public class ValidateTest_testNoNullElementsWithMessage {

    private static final String CUSTOM_MESSAGE = "Custom error message";

    @Test
    public void acceptsArrayWithoutNullElements() {
        Object[] arrayWithoutNulls = { new Object(), new Object() };

        // Should complete without throwing.
        Validate.noNullElements(arrayWithoutNulls, CUSTOM_MESSAGE);
    }

    @Test
    public void rejectsArrayContainingNullElementWithCustomMessage() {
        Object[] arrayWithNull = { new Object(), null };

        ValidationException thrown = assertThrows(
            ValidationException.class,
            () -> Validate.noNullElements(arrayWithNull, CUSTOM_MESSAGE)
        );

        assertEquals(CUSTOM_MESSAGE, thrown.getMessage());
    }
}
