package org.jsoup.helper;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

@SuppressWarnings("deprecation")
public class ValidateTest_testIsTrue {

    @Test
    public void testIsTrue() {
        Validate.isTrue(true);

        boolean validationFailed = false;
        try {
            Validate.isTrue(false);
        } catch (ValidationException e) {
            validationFailed = true;
            assertEquals("Must be true", e.getMessage());
        }

        assertTrue(validationFailed);
    }
}
