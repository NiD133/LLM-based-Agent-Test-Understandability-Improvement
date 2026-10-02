package org.threeten.extra;

import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.Test;

public class TestDays_test_plus_TemporalAmount_null {

    @Test
    public void test_plus_TemporalAmount_null() {
        // Use a non-zero, non-one value to exercise the general code path
        Days days = Days.of(Integer.MIN_VALUE + 1);
        assertThrows(NullPointerException.class, () -> days.plus(null));
    }
}
