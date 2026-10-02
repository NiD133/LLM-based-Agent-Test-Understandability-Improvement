package org.threeten.extra;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

public class TestMonths_test_negated {

    @Test
    public void test_negated_zero_stays_zero() {
        assertEquals(Months.of(0), Months.of(0).negated());
    }

    @Test
    public void test_negated_positive_becomes_negative() {
        assertEquals(Months.of(-12), Months.of(12).negated());
    }

    @Test
    public void test_negated_negative_becomes_positive() {
        assertEquals(Months.of(12), Months.of(-12).negated());
    }

    @Test
    public void test_negated_max_value_does_not_overflow() {
        assertEquals(Months.of(-Integer.MAX_VALUE), Months.of(Integer.MAX_VALUE).negated());
    }
}
