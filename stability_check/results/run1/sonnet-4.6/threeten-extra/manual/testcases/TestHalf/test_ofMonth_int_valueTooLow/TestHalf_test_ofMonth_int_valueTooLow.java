package org.threeten.extra;

import static org.junit.jupiter.api.Assertions.assertThrows;

import java.time.DateTimeException;

import org.junit.jupiter.api.Test;

public class TestHalf_test_ofMonth_int_valueTooLow {

    @Test
    public void test_ofMonth_int_valueTooLow() {
        // month-of-year must be 1–12; 0 is below the valid range
        assertThrows(DateTimeException.class, () -> Half.ofMonth(0));
    }
}
