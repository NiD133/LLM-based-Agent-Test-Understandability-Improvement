package org.jsoup.helper;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

// Tests for Validate.notEmptyParam: verifies that empty and null strings are rejected with the correct message.
@SuppressWarnings("deprecation")
public class ValidateTest_testNotEmptyParam {

    private static final String PARAM_NAME = "param";
    private static final String EXPECTED_MESSAGE = "The 'param' parameter must not be empty.";

    @Test
    public void notEmptyParam_nonEmptyString_doesNotThrow() {
        // A valid non-empty string should pass validation without any exception.
        assertDoesNotThrow(() -> Validate.notEmptyParam("foo", PARAM_NAME));
    }

    @Test
    public void notEmptyParam_emptyString_throwsWithExpectedMessage() {
        ValidationException ex = assertThrows(
            ValidationException.class,
            () -> Validate.notEmptyParam("", PARAM_NAME)
        );
        assertEquals(EXPECTED_MESSAGE, ex.getMessage());
    }

    @Test
    public void notEmptyParam_nullString_throwsWithExpectedMessage() {
        ValidationException ex = assertThrows(
            ValidationException.class,
            () -> Validate.notEmptyParam(null, PARAM_NAME)
        );
        assertEquals(EXPECTED_MESSAGE, ex.getMessage());
    }
}
