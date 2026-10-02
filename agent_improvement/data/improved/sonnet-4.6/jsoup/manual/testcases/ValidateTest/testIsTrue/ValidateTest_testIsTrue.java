package org.jsoup.helper;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class ValidateTest_testIsTrue {

    @Test
    public void testIsTrue() {
        // Validate.isTrue(true) should succeed without throwing
        Validate.isTrue(true);

        // Validate.isTrue(false) must throw a ValidationException with the expected message
        ValidationException ex = assertThrows(ValidationException.class, () -> Validate.isTrue(false));
        assertEquals("Must be true", ex.getMessage());
    }
}
