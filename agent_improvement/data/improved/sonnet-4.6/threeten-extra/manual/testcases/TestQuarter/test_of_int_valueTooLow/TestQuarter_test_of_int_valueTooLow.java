package org.threeten.extra;

import static org.junit.jupiter.api.Assertions.assertThrows;

import java.time.DateTimeException;

import org.junit.jupiter.api.Test;

public class TestQuarter_test_of_int_valueTooLow {

    @Test
    public void test_of_int_valueTooLow() {
        // Quarter.of() accepts values 1-4; 0 is below the valid range
        assertThrows(DateTimeException.class, () -> Quarter.of(0));
    }
}
