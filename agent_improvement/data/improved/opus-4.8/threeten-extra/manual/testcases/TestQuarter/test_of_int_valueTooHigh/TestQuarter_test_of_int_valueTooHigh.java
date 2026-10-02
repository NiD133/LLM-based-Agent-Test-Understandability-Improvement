package org.threeten.extra;

import static org.junit.jupiter.api.Assertions.assertThrows;

import java.time.DateTimeException;

import org.junit.jupiter.api.Test;

/**
 * Tests that {@link Quarter#of(int)} rejects values above the valid range.
 * <p>
 * Valid quarter values are 1 (Q1) to 4 (Q4); any higher value must be rejected.
 */
public class TestQuarter_test_of_int_valueTooHigh {

    @Test
    public void of_withValueAboveQ4_throwsDateTimeException() {
        int valueTooHigh = 5;
        assertThrows(DateTimeException.class, () -> Quarter.of(valueTooHigh));
    }
}
