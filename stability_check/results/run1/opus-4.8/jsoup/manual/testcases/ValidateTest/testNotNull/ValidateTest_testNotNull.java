package org.jsoup.helper;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertThrows;

// keeps tests for ensureNotNull
@SuppressWarnings("deprecation")
public class ValidateTest_testNotNull {

    @Test
    public void testNotNull() {
        // A non-null argument passes validation without throwing.
        assertDoesNotThrow(() -> Validate.notNull("foo"));

        // A null argument is rejected with an IllegalArgumentException.
        assertThrows(IllegalArgumentException.class, () -> Validate.notNull(null));
    }
}
