package org.threeten.extra;

import static org.junit.jupiter.api.Assertions.assertThrows;

import java.time.DateTimeException;

import org.junit.jupiter.api.Test;

public class TestHalf_test_ofMonth_int_valueTooHigh {

    @Test
    public void test_ofMonth_int_valueTooHigh() {
        // Month values are 1-12; 13 is above the valid range and must throw
        assertThrows(DateTimeException.class, () -> Half.ofMonth(13));
    }
}
