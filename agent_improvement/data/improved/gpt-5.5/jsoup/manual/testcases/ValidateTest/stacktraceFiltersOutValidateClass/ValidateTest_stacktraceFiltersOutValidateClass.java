package org.jsoup.helper;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

@SuppressWarnings("deprecation")
public class ValidateTest_stacktraceFiltersOutValidateClass {

    @Test
    void stacktraceFiltersOutValidateClass() {
        boolean validationExceptionThrown = false;

        try {
            Validate.notNull(null);
        } catch (ValidationException e) {
            validationExceptionThrown = true;

            assertEquals("Object must not be null", e.getMessage());
            StackTraceElement[] stackTrace = e.getStackTrace();
            assertValidateClassIsFilteredOut(stackTrace);
            assertTrue(stackTrace.length >= 1);
        }

        Assertions.assertTrue(validationExceptionThrown);
    }

    private static void assertValidateClassIsFilteredOut(StackTraceElement[] stackTrace) {
        for (StackTraceElement trace : stackTrace) {
            assertNotEquals(trace.getClassName(), Validate.class.getName());
        }
    }
}
