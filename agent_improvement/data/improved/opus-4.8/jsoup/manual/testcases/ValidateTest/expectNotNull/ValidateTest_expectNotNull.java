package org.jsoup.helper;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertSame;
import static org.junit.jupiter.api.Assertions.assertThrows;

// keeps tests for ensureNotNull
@SuppressWarnings("deprecation")
public class ValidateTest_expectNotNull {

    @Test
    void returnsSameObjectWhenNotNull() {
        String input = "Foo";

        String result = Validate.expectNotNull(input);

        assertSame(input, result);
    }

    @Test
    void throwsValidationExceptionWhenNull() {
        String nullInput = null;

        ValidationException thrown = assertThrows(
                ValidationException.class,
                () -> Validate.expectNotNull(nullInput));

        assertEquals("Object must not be null", thrown.getMessage());
    }
}
