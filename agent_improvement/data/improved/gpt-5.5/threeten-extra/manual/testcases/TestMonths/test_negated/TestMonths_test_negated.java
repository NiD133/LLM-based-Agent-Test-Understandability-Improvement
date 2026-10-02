package org.threeten.extra;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

public class TestMonths_test_negated {

    @Test
    public void test_negated() {
        assertEquals(Months.of(0), Months.of(0).negated());
        assertEquals(Months.of(-12), Months.of(12).negated());
        assertEquals(Months.of(12), Months.of(-12).negated());
        assertEquals(Months.of(-Integer.MAX_VALUE), Months.of(Integer.MAX_VALUE).negated());
    }
}
