package org.threeten.extra;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

public class TestMinutes_test_negated {

    // negating zero yields zero (identity)
    @Test
    public void test_negated_zero() {
        assertEquals(Minutes.of(0), Minutes.of(0).negated());
    }

    // negating a positive value yields the corresponding negative value
    @Test
    public void test_negated_positive_becomes_negative() {
        assertEquals(Minutes.of(-12), Minutes.of(12).negated());
    }

    // negating a negative value yields the corresponding positive value
    @Test
    public void test_negated_negative_becomes_positive() {
        assertEquals(Minutes.of(12), Minutes.of(-12).negated());
    }

    // negating Integer.MAX_VALUE yields -Integer.MAX_VALUE (no overflow at max value)
    @Test
    public void test_negated_max_value() {
        assertEquals(Minutes.of(-Integer.MAX_VALUE), Minutes.of(Integer.MAX_VALUE).negated());
    }
}
