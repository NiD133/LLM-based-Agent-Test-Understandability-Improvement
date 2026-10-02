package org.jsoup.helper;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class ValidateTest_testAssertFail {

    @Test
    public void testAssertFail() {
        ValidationException exception = assertThrows(
            ValidationException.class,
            () -> Validate.assertFail("This should fail")
        );

        assertEquals("This should fail", exception.getMessage());
    }
}
