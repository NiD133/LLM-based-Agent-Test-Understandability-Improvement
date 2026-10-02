package org.threeten.extra;

import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.Test;

public class TestHours_test_minus_TemporalAmount_null {

    @Test
    public void test_minus_TemporalAmount_null() {
        Hours hours = Hours.of(Integer.MIN_VALUE + 1);
        assertThrows(NullPointerException.class, () -> hours.minus(null));
    }
}
