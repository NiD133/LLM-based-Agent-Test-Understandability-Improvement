package org.threeten.extra;

import static org.junit.jupiter.api.Assertions.assertThrows;

import java.time.DateTimeException;

import org.junit.jupiter.api.Test;

public class TestDayOfYear_test_of_int_tooHigh {

    @Test
    public void test_of_int_tooHigh() {
        assertThrows(DateTimeException.class, () -> DayOfYear.of(367));
    }
}
