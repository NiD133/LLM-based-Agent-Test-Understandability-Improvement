package org.threeten.extra;

import static org.junit.jupiter.api.Assertions.assertThrows;

import java.time.DateTimeException;

import org.junit.jupiter.api.Test;

public class TestQuarter_test_ofMonth_int_valueTooHigh {

    @Test
    public void test_ofMonth_int_valueTooHigh() {
        // Month 13 exceeds the valid range (1–12), so ofMonth must reject it
        assertThrows(DateTimeException.class, () -> Quarter.ofMonth(13));
    }
}
