package org.jsoup.helper;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class ValidateTest_testNotNull {

    @Test
    public void testNotNull() {
        assertDoesNotThrow(() -> Validate.notNull("foo"));
        assertThrows(IllegalArgumentException.class, () -> Validate.notNull(null));
    }
}
