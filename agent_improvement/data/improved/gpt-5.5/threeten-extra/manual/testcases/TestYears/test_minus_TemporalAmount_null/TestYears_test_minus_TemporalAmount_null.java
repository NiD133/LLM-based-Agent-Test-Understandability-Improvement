package org.threeten.extra;

import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.Test;

public class TestYears_test_minus_TemporalAmount_null {

    @Test
    public void test_minus_TemporalAmount_null() {
        Years years = Years.of(Integer.MIN_VALUE + 1);

        assertThrows(NullPointerException.class, () -> years.minus(null));
    }
}
