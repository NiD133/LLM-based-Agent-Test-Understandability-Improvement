package org.threeten.extra;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

public class TestMonths_test_abs {

    @Test
    public void test_abs() {
        // Zero stays zero
        assertEquals(Months.of(0), Months.of(0).abs());

        // Positive value is unchanged
        assertEquals(Months.of(12), Months.of(12).abs());

        // Negative value becomes positive
        assertEquals(Months.of(12), Months.of(-12).abs());

        // MAX_VALUE stays unchanged (no overflow risk)
        assertEquals(Months.of(Integer.MAX_VALUE), Months.of(Integer.MAX_VALUE).abs());

        // Negated MAX_VALUE becomes positive MAX_VALUE
        assertEquals(Months.of(Integer.MAX_VALUE), Months.of(-Integer.MAX_VALUE).abs());
    }
}
