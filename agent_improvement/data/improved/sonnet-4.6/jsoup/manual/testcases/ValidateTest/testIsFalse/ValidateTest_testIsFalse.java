package org.jsoup.helper;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

@SuppressWarnings("deprecation")
public class ValidateTest_testIsFalse {

    @Test
    public void testIsFalse() {
        // isFalse(false) must not throw
        assertDoesNotThrow(() -> Validate.isFalse(false));

        // isFalse(true) must throw with the expected message
        ValidationException ex = assertThrows(ValidationException.class, () -> Validate.isFalse(true));
        assertEquals("Must be false", ex.getMessage());
    }
}
