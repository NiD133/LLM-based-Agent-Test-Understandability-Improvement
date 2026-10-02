package org.threeten.extra;

import static org.junit.jupiter.api.Assertions.assertThrows;

import java.time.DateTimeException;

import org.junit.jupiter.api.Test;

public class TestQuarter_test_ofMonth_int_valueTooLow {

    @Test
    public void test_ofMonth_int_valueTooLow() {
        assertThrows(DateTimeException.class, () -> Quarter.ofMonth(0));
    }
}
