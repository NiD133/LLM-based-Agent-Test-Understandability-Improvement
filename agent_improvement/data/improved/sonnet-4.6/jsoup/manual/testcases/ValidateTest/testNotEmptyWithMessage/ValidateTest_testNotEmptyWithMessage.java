package org.jsoup.helper;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class ValidateTest_testNotEmptyWithMessage {

    private static final String CUSTOM_MSG = "Custom error message";

    @Test
    public void testNotEmptyWithMessage() {
        // Non-empty string: no exception expected
        Validate.notEmpty("foo", CUSTOM_MSG);

        // Empty string: must throw with the custom message
        ValidationException emptyEx = assertThrows(ValidationException.class,
                () -> Validate.notEmpty("", CUSTOM_MSG));
        assertEquals(CUSTOM_MSG, emptyEx.getMessage());

        // Null string: must throw with the custom message
        ValidationException nullEx = assertThrows(ValidationException.class,
                () -> Validate.notEmpty(null, CUSTOM_MSG));
        assertEquals(CUSTOM_MSG, nullEx.getMessage());
    }
}
