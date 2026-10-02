package org.threeten.extra.chrono;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.time.chrono.ChronoPeriod;

import org.junit.jupiter.api.Test;

@SuppressWarnings({ "static-method" })
public class TestInternationalFixedChronology_test_plus_Period {

    @Test
    public void test_plus_Period() {
        InternationalFixedDate startDate = InternationalFixedDate.of(2014, 5, 26);
        ChronoPeriod periodToAdd = InternationalFixedChronology.INSTANCE.period(0, 2, 3);
        InternationalFixedDate expectedDate = InternationalFixedDate.of(2014, 8, 1);

        InternationalFixedDate actualDate = startDate.plus(periodToAdd);

        assertEquals(expectedDate, actualDate);
    }
}
