package org.threeten.extra.chrono;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.time.chrono.ChronoPeriod;

import org.junit.jupiter.api.Test;

@SuppressWarnings({ "static-method" })
public class TestInternationalFixedChronology_test_plus_Period {

    @Test
    public void test_plus_Period() {
        // Adding a period of 0 years, 2 months, 3 days to 2014/05/26 should yield 2014/08/01
        InternationalFixedDate startDate = InternationalFixedDate.of(2014, 5, 26);
        ChronoPeriod period = InternationalFixedChronology.INSTANCE.period(0, 2, 3);
        InternationalFixedDate expectedDate = InternationalFixedDate.of(2014, 8, 1);

        assertEquals(expectedDate, startDate.plus(period));
    }
}
