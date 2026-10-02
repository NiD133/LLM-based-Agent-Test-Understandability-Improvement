package org.threeten.extra;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.time.Period;

import org.junit.jupiter.api.Test;

public class TestMonths_test_from_yearsAndMonths {

    /**
     * {@link Months#from(java.time.temporal.TemporalAmount)} should convert a
     * {@link Period} that has both years and months into a total month count,
     * using 12 months per year. For 3 years and 5 months this is
     * (3 * 12) + 5 = 41 months.
     */
    @Test
    public void test_from_yearsAndMonths() {
        Period threeYearsFiveMonths = Period.of(3, 5, 0);

        Months actual = Months.from(threeYearsFiveMonths);

        assertEquals(Months.of(41), actual);
    }
}
