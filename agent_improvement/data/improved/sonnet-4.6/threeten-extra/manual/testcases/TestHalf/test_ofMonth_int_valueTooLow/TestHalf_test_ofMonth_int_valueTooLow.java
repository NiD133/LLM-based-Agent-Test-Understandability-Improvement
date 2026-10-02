package org.threeten.extra;

import static org.junit.jupiter.api.Assertions.assertThrows;

import java.time.DateTimeException;

import org.junit.jupiter.api.Test;

public class TestHalf_test_ofMonth_int_valueTooLow {

    @Test
    public void test_ofMonth_int_valueTooLow() {
        assertThrows(DateTimeException.class, () -> Half.ofMonth(0));
    }
}
