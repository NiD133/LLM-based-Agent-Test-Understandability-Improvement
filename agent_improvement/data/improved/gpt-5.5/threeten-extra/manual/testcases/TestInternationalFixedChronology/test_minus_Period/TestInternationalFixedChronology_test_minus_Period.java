package org.threeten.extra.chrono;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.time.chrono.ChronoPeriod;

import org.junit.jupiter.api.Test;

@SuppressWarnings("static-method")
public class TestInternationalFixedChronology_test_minus_Period {

    @Test
    public void test_minus_Period() {
        InternationalFixedDate startDate = InternationalFixedDate.of(2014, 5, 26);
        ChronoPeriod twoMonthsAndThreeDays = InternationalFixedChronology.INSTANCE.period(0, 2, 3);
        InternationalFixedDate expectedDate = InternationalFixedDate.of(2014, 3, 23);

        assertEquals(expectedDate, startDate.minus(twoMonthsAndThreeDays));
    }
}
