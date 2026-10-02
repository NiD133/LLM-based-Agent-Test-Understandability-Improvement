package org.jsoup.helper;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

// keeps tests for ensureNotNull
@SuppressWarnings("deprecation")
public class ValidateTest_testWtf {

    @Test
    public void wtfThrowsIllegalStateExceptionWithGivenMessage() {
        IllegalStateException thrown = assertThrows(
            IllegalStateException.class,
            () -> Validate.wtf("Unexpected state reached")
        );

        assertEquals("Unexpected state reached", thrown.getMessage());
    }
}
