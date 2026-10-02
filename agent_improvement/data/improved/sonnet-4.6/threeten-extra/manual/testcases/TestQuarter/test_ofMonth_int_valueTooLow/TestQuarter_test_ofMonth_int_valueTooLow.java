package org.threeten.extra;

import static org.junit.jupiter.api.Assertions.assertThrows;

import java.time.DateTimeException;

import org.junit.jupiter.api.Test;

public class TestQuarter_test_ofMonth_int_valueTooLow {

    @Test
    public void test_ofMonth_int_valueTooLow() {
        // Month 0 is below the valid range (1–12), so ofMonth must reject it
        assertThrows(DateTimeException.class, () -> Quarter.ofMonth(0));
    }
}
