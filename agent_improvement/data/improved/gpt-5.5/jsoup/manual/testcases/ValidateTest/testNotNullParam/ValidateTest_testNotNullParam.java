package org.jsoup.helper;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class ValidateTest_testNotNullParam {
    private static final String PARAMETER_NAME = "param";
    private static final String EXPECTED_NULL_PARAMETER_MESSAGE =
            "The parameter 'param' must not be null.";

    @Test
    public void testNotNullParam() {
        Object nonNullObject = new Object();

        assertDoesNotThrow(() -> Validate.notNullParam(nonNullObject, PARAMETER_NAME));

        ValidationException thrown = assertThrows(
                ValidationException.class,
                () -> Validate.notNullParam(null, PARAMETER_NAME)
        );
        assertEquals(EXPECTED_NULL_PARAMETER_MESSAGE, thrown.getMessage());
    }
}
