package org.threeten.extra;

import static org.junit.jupiter.api.Assertions.assertThrows;

import java.time.DateTimeException;

import org.junit.jupiter.api.Test;

/**
 * Tests that {@link AmPm#of(int)} rejects values below the valid range (0..1).
 */
public class TestAmPm_test_of_int_valueTooLow {

    @Test
    public void of_withValueBelowValidRange_throwsDateTimeException() {
        int valueTooLow = -1;

        assertThrows(DateTimeException.class, () -> AmPm.of(valueTooLow));
    }
}
