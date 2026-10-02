package org.threeten.extra.chrono;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.time.chrono.ChronoPeriod;

import org.junit.jupiter.api.Test;

/**
 * Tests subtracting a {@link ChronoPeriod} from a {@link PaxDate}.
 */
@SuppressWarnings({ "static-method" })
public class TestPaxChronology_test_minus_Period {

    @Test
    public void test_minus_Period() {
        // Start at Pax 2014-05-26 and subtract a period of 0 years, 2 months and 3 days.
        PaxDate startDate = PaxDate.of(2014, 5, 26);
        ChronoPeriod twoMonthsThreeDays = PaxChronology.INSTANCE.period(0, 2, 3);

        PaxDate result = startDate.minus(twoMonthsThreeDays);

        assertEquals(PaxDate.of(2014, 3, 23), result);
    }
}
