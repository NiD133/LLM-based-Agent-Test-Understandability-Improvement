package org.threeten.extra.chrono;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.time.chrono.ChronoPeriod;

import org.junit.jupiter.api.Test;

@SuppressWarnings({ "static-method" })
public class TestSymmetry010Chronology_test_plus_Period {

    /**
     * Adding a period of 2 months and 8 days to 2014-05-21 should yield 2014-07-29.
     */
    @Test
    public void test_plus_Period() {
        Symmetry010Date startDate = Symmetry010Date.of(2014, 5, 21);
        ChronoPeriod twoMonthsAndEightDays = Symmetry010Chronology.INSTANCE.period(0, 2, 8);

        Symmetry010Date result = startDate.plus(twoMonthsAndEightDays);

        assertEquals(Symmetry010Date.of(2014, 7, 29), result);
    }
}
