package org.jsoup.helper;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

// keeps tests for ensureNotNull
@SuppressWarnings("deprecation")
public class ValidateTest_nonnullParam {

    @Test
    void nonnullParam() {
        boolean threw = true;
        try {
            Validate.notNullParam(null, "foo");
        } catch (ValidationException e) {
            assertEquals("The parameter 'foo' must not be null.", e.getMessage());
        }
        assertTrue(threw);
    }
}
