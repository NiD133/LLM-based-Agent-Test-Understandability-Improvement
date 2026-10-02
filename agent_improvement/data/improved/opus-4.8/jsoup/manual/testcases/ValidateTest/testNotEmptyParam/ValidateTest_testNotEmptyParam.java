package org.jsoup.helper;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

// keeps tests for ensureNotNull
@SuppressWarnings("deprecation")
public class ValidateTest_testNotEmptyParam {

    private static final String PARAM_NAME = "param";
    private static final String EXPECTED_MESSAGE = "The 'param' parameter must not be empty.";

    @Test
    public void testNotEmptyParam() {
        // A non-empty string passes validation without throwing.
        assertDoesNotThrow(() -> Validate.notEmptyParam("foo", PARAM_NAME));

        // An empty string is rejected with a message naming the parameter.
        ValidationException emptyFailure = assertThrows(ValidationException.class,
                () -> Validate.notEmptyParam("", PARAM_NAME));
        assertEquals(EXPECTED_MESSAGE, emptyFailure.getMessage());

        // A null string is rejected with the same message.
        ValidationException nullFailure = assertThrows(ValidationException.class,
                () -> Validate.notEmptyParam(null, PARAM_NAME));
        assertEquals(EXPECTED_MESSAGE, nullFailure.getMessage());
    }
}
