package org.jsoup.helper;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

// keeps tests for ensureNotNull
@SuppressWarnings("deprecation")
public class ValidateTest_nonnullParam {

    @Test
    void nonnullParam() {
        // notNullParam should reject a null argument and name the offending parameter.
        ValidationException thrown = assertThrows(
            ValidationException.class,
            () -> Validate.notNullParam(null, "foo")
        );

        assertEquals("The parameter 'foo' must not be null.", thrown.getMessage());
    }
}
