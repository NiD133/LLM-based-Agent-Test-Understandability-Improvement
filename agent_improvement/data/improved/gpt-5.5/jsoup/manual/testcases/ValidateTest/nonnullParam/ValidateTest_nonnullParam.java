package org.jsoup.helper;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class ValidateTest_nonnullParam {
    private static final String PARAMETER_NAME = "foo";
    private static final String EXPECTED_NULL_PARAMETER_MESSAGE =
            "The parameter 'foo' must not be null.";

    @Test
    void nonnullParam() {
        boolean validationCompleted = true;

        try {
            Validate.notNullParam(null, PARAMETER_NAME);
        } catch (ValidationException exception) {
            assertEquals(EXPECTED_NULL_PARAMETER_MESSAGE, exception.getMessage());
        }

        assertTrue(validationCompleted);
    }
}
