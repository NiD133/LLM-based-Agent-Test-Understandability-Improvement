package org.threeten.extra;

import static org.junit.jupiter.api.Assertions.assertThrows;

import java.time.DateTimeException;

import org.junit.jupiter.api.Test;

public class TestQuarter_test_ofMonth_int_valueTooHigh {

    private static final int MONTH_AFTER_DECEMBER = 13;

    @Test
    public void test_ofMonth_int_valueTooHigh() {
        assertThrows(DateTimeException.class, () -> Quarter.ofMonth(MONTH_AFTER_DECEMBER));
    }
}
