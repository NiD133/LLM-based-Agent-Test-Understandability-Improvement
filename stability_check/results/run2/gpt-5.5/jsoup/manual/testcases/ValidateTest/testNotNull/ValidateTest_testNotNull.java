package org.jsoup.helper;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class ValidateTest_testNotNull {

    @Test
    public void testNotNull() {
        Validate.notNull("foo");

        boolean validationExceptionThrown = false;
        try {
            Validate.notNull(null);
        } catch (IllegalArgumentException expected) {
            validationExceptionThrown = true;
        }

        Assertions.assertTrue(validationExceptionThrown);
    }
}
