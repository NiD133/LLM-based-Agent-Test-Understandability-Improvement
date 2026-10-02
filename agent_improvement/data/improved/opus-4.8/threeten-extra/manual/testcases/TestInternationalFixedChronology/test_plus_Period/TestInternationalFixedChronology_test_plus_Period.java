package org.threeten.extra.chrono;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

/**
 * Tests that adding a {@link java.time.chrono.ChronoPeriod} to an
 * {@link InternationalFixedDate} advances the date by the period's
 * years, months and days.
 */
@SuppressWarnings({ "static-method" })
public class TestInternationalFixedChronology_test_plus_Period {

    @Test
    public void test_plus_Period() {
        InternationalFixedDate startDate = InternationalFixedDate.of(2014, 5, 26);

        // Add a period of 0 years, 2 months and 3 days.
        InternationalFixedDate result =
            startDate.plus(InternationalFixedChronology.INSTANCE.period(0, 2, 3));

        InternationalFixedDate expectedDate = InternationalFixedDate.of(2014, 8, 1);
        assertEquals(expectedDate, result);
    }
}
