package org.threeten.extra.chrono;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.time.chrono.ChronoPeriod;

import org.junit.jupiter.api.Test;

/**
 * Tests that subtracting a {@link ChronoPeriod} from a {@link Symmetry454Date}
 * shifts the date by the expected number of years, months and days.
 */
@SuppressWarnings({ "static-method" })
public class TestSymmetry454Chronology_test_minus_Period {

    @Test
    public void test_minus_Period() {
        // Subtracting 2 months and 3 days from 2014-05-26 gives 2014-03-23.
        ChronoPeriod twoMonthsThreeDays = Symmetry454Chronology.INSTANCE.period(0, 2, 3);
        Symmetry454Date startDate = Symmetry454Date.of(2014, 5, 26);

        Symmetry454Date result = startDate.minus(twoMonthsThreeDays);

        assertEquals(Symmetry454Date.of(2014, 3, 23), result);
    }
}
