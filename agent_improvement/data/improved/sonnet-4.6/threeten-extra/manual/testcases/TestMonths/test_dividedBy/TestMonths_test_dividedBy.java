package org.threeten.extra;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

public class TestMonths_test_dividedBy {

    //-----------------------------------------------------------------------
    public static Object[][] data_valid() {
        return new Object[][] { { "P0M", 0 }, { "P1M", 1 }, { "P2M", 2 }, { "P123456789M", 123456789 }, { "P+0M", 0 }, { "P+2M", 2 }, { "P-0M", 0 }, { "P-2M", -2 }, { "P0Y", 0 }, { "P1Y", 12 }, { "P2Y", 24 }, { "P1234567Y", 1234567 * 12 }, { "P+0Y", 0 }, { "P+2Y", 24 }, { "P-0Y", 0 }, { "P-2Y", -24 }, { "P0Y0M", 0 }, { "P2Y3M", 27 }, { "P+2Y3M", 27 }, { "P2Y+3M", 27 }, { "P-2Y3M", -21 }, { "P2Y-3M", 21 }, { "P-2Y-3M", -27 } };
    }

    public static Object[][] data_invalid() {
        return new Object[][] { { "P3W" }, { "P3D" }, { "P3Q" }, { "P1M2Y" }, { "3" }, { "-3" }, { "3M" }, { "-3M" }, { "P3" }, { "P-3" }, { "PM" } };
    }

    //-----------------------------------------------------------------------
    // dividedBy(int) uses integer division (truncated toward zero).
    // For example, 12 / 5 = 2 (not 2.4), and a negative divisor negates the result.
    @Test
    public void test_dividedBy() {
        Months twelveMonths = Months.of(12);

        // Dividing by 1 is a no-op
        assertEquals(Months.of(12), twelveMonths.dividedBy(1));

        // Exact division
        assertEquals(Months.of(6), twelveMonths.dividedBy(2));
        assertEquals(Months.of(4), twelveMonths.dividedBy(3));
        assertEquals(Months.of(3), twelveMonths.dividedBy(4));

        // Truncated division: 12/5 = 2 (remainder discarded), 12/6 = 2
        assertEquals(Months.of(2), twelveMonths.dividedBy(5));
        assertEquals(Months.of(2), twelveMonths.dividedBy(6));

        // Negative divisor: 12 / -3 = -4
        assertEquals(Months.of(-4), twelveMonths.dividedBy(-3));
    }
}
