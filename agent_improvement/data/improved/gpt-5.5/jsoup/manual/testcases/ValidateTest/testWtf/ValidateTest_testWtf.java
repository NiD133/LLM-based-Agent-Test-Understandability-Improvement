package org.jsoup.helper;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class ValidateTest_testWtf {

    private static final String UNEXPECTED_STATE_MESSAGE = "Unexpected state reached";

    @Test
    public void testWtf() {
        IllegalStateException thrown = assertThrows(
                IllegalStateException.class,
                () -> Validate.wtf(UNEXPECTED_STATE_MESSAGE)
        );

        assertEquals(UNEXPECTED_STATE_MESSAGE, thrown.getMessage());
    }
}
