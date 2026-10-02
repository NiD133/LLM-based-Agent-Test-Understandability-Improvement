package org.jsoup.helper;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

// keeps tests for ensureNotNull
@SuppressWarnings("deprecation")
public class ValidateTest_stacktraceFiltersOutValidateClass {

    @Test
    void stacktraceFiltersOutValidateClass() {
        // A failed validation should report the caller's location, not the
        // internal Validate helper, so Validate must be scrubbed from the stack trace.
        ValidationException thrown = assertThrows(
            ValidationException.class,
            () -> Validate.notNull(null));

        assertEquals("Object must not be null", thrown.getMessage());

        StackTraceElement[] stackTrace = thrown.getStackTrace();
        assertTrue(stackTrace.length >= 1, "Expected a non-empty stack trace");
        for (StackTraceElement frame : stackTrace) {
            assertNotEquals(Validate.class.getName(), frame.getClassName(),
                "Validate should be filtered out of the stack trace");
        }
    }
}
