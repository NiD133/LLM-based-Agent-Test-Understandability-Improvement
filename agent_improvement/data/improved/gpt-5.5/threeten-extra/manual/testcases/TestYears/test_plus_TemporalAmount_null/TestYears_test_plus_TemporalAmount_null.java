package org.threeten.extra;

import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.Test;

public class TestYears_test_plus_TemporalAmount_null {

    @Test
    public void test_plus_TemporalAmount_null() {
        assertThrows(NullPointerException.class, () -> {
            Years years = Years.of(Integer.MIN_VALUE + 1);
            years.plus(null);
        });
    }
}
