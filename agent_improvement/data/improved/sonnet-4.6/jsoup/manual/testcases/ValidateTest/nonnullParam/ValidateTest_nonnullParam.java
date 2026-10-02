package org.jsoup.helper;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

@SuppressWarnings("deprecation")
public class ValidateTest_nonnullParam {

    @Test
    void nonnullParam_throwsValidationException_withParameterName() {
        ValidationException ex = assertThrows(ValidationException.class,
            () -> Validate.notNullParam(null, "foo"));
        assertEquals("The parameter 'foo' must not be null.", ex.getMessage());
    }
}
