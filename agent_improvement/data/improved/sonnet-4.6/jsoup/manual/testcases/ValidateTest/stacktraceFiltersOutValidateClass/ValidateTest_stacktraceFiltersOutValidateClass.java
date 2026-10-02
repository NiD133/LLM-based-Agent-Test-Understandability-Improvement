package org.jsoup.helper;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

@SuppressWarnings("deprecation")
public class ValidateTest_stacktraceFiltersOutValidateClass {

    @Test
    void stacktraceFiltersOutValidateClass() {
        // Validate.notNull(null) should throw a ValidationException whose stack trace
        // does not include any frames from the Validate class itself.
        ValidationException exception = assertThrows(
                ValidationException.class,
                () -> Validate.notNull(null)
        );

        assertEquals("Object must not be null", exception.getMessage());

        StackTraceElement[] stackTrace = exception.getStackTrace();
        assertTrue(stackTrace.length >= 1, "Stack trace should have at least one frame");

        for (StackTraceElement frame : stackTrace) {
            assertNotEquals(
                    Validate.class.getName(),
                    frame.getClassName(),
                    "Stack trace must not contain frames from Validate"
            );
        }
    }
}
