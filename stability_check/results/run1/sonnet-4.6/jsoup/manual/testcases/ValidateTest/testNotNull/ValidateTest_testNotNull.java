package org.jsoup.helper;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

// Tests for Validate.notNull: verifies that non-null values pass and null values throw
public class ValidateTest_testNotNull {

    @Test
    public void testNotNull() {
        assertDoesNotThrow(() -> Validate.notNull("foo"));
        assertThrows(IllegalArgumentException.class, () -> Validate.notNull(null));
    }
}
