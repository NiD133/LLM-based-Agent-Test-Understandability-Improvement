package org.jsoup.helper;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class ValidateTest_testWtf {

    @Test
    public void testWtf() {
        String message = "Unexpected state reached";

        IllegalStateException exception = assertThrows(
            IllegalStateException.class,
            () -> Validate.wtf(message)
        );

        assertEquals(message, exception.getMessage());
    }
}
