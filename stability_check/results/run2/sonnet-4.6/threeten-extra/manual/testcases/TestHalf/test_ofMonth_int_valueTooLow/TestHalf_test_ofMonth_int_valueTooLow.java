package org.threeten.extra;

import static org.junit.jupiter.api.Assertions.assertThrows;

import java.time.DateTimeException;

import org.junit.jupiter.api.Test;

public class TestHalf_test_ofMonth_int_valueTooLow {

    // Valid month values are 1–12; month 0 is below the minimum and must be rejected.
    @Test
    public void test_ofMonth_int_valueTooLow() {
        assertThrows(DateTimeException.class, () -> Half.ofMonth(0));
    }
}
