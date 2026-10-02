package org.jsoup.helper;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

// keeps tests for ensureNotNull
@SuppressWarnings("deprecation")
public class ValidateTest_testNotNullParam {

    @Test
    public void testNotNullParam() {
        // A non-null parameter passes validation without throwing.
        Object nonNullParam = new Object();
        assertDoesNotThrow(() -> Validate.notNullParam(nonNullParam, "param"));

        // A null parameter is rejected with a ValidationException naming the parameter.
        ValidationException thrown = assertThrows(
            ValidationException.class,
            () -> Validate.notNullParam(null, "param"));
        assertEquals("The parameter 'param' must not be null.", thrown.getMessage());
    }
}
