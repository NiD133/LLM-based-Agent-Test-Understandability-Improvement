package org.threeten.extra;

import static org.junit.jupiter.api.Assertions.assertThrows;

import java.time.DateTimeException;

import org.junit.jupiter.api.Test;

/**
 * Tests that {@link Quarter#of(int)} rejects a value below the valid range of 1 to 4.
 */
public class TestQuarter_test_of_int_valueTooLow {

    @Test
    public void of_withValueBelowRange_throwsDateTimeException() {
        // 0 is just below the lowest valid quarter value (1 = Q1).
        assertThrows(DateTimeException.class, () -> Quarter.of(0));
    }
}
