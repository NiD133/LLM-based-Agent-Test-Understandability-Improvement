package org.jsoup.helper;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertSame;
import static org.junit.jupiter.api.Assertions.assertTrue;

@SuppressWarnings("deprecation")
public class ValidateTest_testEnsureNotNull {

    @Test
    public void testEnsureNotNull() {
        Object nonNullObject = new Object();

        assertSame(nonNullObject, Validate.ensureNotNull(nonNullObject));

        boolean validationExceptionThrown = false;
        try {
            Validate.ensureNotNull(null);
        } catch (ValidationException e) {
            validationExceptionThrown = true;
            assertEquals("Object must not be null", e.getMessage());
        }
        assertTrue(validationExceptionThrown);
    }
}
