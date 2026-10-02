package org.threeten.extra;

import static org.junit.jupiter.api.Assertions.assertThrows;

import java.time.temporal.TemporalAmount;

import org.junit.jupiter.api.Test;

public class TestYears_test_minus_TemporalAmount_null {

    @Test
    public void test_minus_TemporalAmount_null() {
        Years years = Years.of(Integer.MIN_VALUE + 1);
        TemporalAmount nullAmount = null;
        assertThrows(NullPointerException.class, () -> years.minus(nullAmount));
    }
}
