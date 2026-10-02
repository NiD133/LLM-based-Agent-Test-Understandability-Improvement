package org.jsoup.helper;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class ValidateTest_testNotNull {

    @Test
    public void testNotNull() {
        Assertions.assertDoesNotThrow(() -> Validate.notNull("foo"));
        Assertions.assertThrows(IllegalArgumentException.class, () -> Validate.notNull(null));
    }
}
