package org.jsoup.helper;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class ValidateTest_testNotEmptyParam {
    private static final String PARAMETER_NAME = "param";
    private static final String EXPECTED_EMPTY_PARAMETER_MESSAGE =
        "The 'param' parameter must not be empty.";

    @Test
    public void testNotEmptyParam() {
        Validate.notEmptyParam("foo", PARAMETER_NAME);

        assertEmptyParameterFails("");
        assertEmptyParameterFails(null);
    }

    private static void assertEmptyParameterFails(String value) {
        ValidationException exception = assertThrows(
            ValidationException.class,
            () -> Validate.notEmptyParam(value, PARAMETER_NAME)
        );

        assertEquals(EXPECTED_EMPTY_PARAMETER_MESSAGE, exception.getMessage());
    }
}
