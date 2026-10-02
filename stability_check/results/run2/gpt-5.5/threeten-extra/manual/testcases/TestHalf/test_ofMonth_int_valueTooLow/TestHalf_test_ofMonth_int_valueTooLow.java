package org.threeten.extra;

import static org.junit.jupiter.api.Assertions.assertThrows;

import java.time.DateTimeException;

import org.junit.jupiter.api.Test;

public class TestHalf_test_ofMonth_int_valueTooLow {

    private static final int MONTH_BEFORE_JANUARY = 0;

    @Test
    public void test_ofMonth_int_valueTooLow() {
        assertThrows(DateTimeException.class, () -> Half.ofMonth(MONTH_BEFORE_JANUARY));
    }
}
