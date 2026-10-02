package org.jsoup.helper;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

@SuppressWarnings("deprecation")
public class ValidateTest_testAssertFail {

    @Test
    public void testAssertFail() {
        // assertFail must throw a ValidationException carrying the provided message
        ValidationException exception = assertThrows(ValidationException.class, () ->
            Validate.assertFail("This should fail")
        );
        assertEquals("This should fail", exception.getMessage());
    }
}
