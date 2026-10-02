package org.threeten.extra;

import static org.junit.jupiter.api.Assertions.assertThrows;

import java.time.DateTimeException;

import org.junit.jupiter.api.Test;

public class TestQuarter_test_of_int_valueTooHigh {

    @Test
    public void test_of_int_valueTooHigh() {
        int quarterAboveValidRange = 5;

        assertThrows(DateTimeException.class, () -> Quarter.of(quarterAboveValidRange));
    }
}
