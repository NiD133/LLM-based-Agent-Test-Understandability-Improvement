package org.jsoup.helper;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class ValidateTest_testNotNullParam {

    @Test
    public void notNullParam_nonNullObject_doesNotThrow() {
        Object obj = new Object();
        assertDoesNotThrow(() -> Validate.notNullParam(obj, "param"));
    }

    @Test
    public void notNullParam_nullObject_throwsWithParamName() {
        ValidationException ex = assertThrows(
            ValidationException.class,
            () -> Validate.notNullParam(null, "param")
        );
        assertEquals("The parameter 'param' must not be null.", ex.getMessage());
    }
}
