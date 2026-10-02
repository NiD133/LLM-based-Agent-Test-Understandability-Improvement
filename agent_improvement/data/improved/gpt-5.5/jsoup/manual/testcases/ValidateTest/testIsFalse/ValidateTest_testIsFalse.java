package org.jsoup.helper;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class ValidateTest_testIsFalse {

    @Test
    public void testIsFalse() {
        Validate.isFalse(false);

        ValidationException exception = assertThrows(
            ValidationException.class,
            () -> Validate.isFalse(true)
        );
        assertEquals("Must be false", exception.getMessage());
    }
}
