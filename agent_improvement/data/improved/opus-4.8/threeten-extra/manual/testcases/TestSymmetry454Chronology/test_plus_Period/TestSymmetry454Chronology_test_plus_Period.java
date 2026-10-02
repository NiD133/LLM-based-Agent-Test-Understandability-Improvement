package org.threeten.extra.chrono;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.time.chrono.ChronoPeriod;

import org.junit.jupiter.api.Test;

/**
 * Tests that adding a {@link ChronoPeriod} to a {@link Symmetry454Date} advances
 * the date by the period's years, months and days.
 */
@SuppressWarnings({ "static-method" })
public class TestSymmetry454Chronology_test_plus_Period {

    @Test
    public void plus_periodOfMonthsAndDays_addsMonthsThenDays() {
        Symmetry454Date startDate = Symmetry454Date.of(2014, 5, 21);
        // A period of 0 years, 2 months and 8 days.
        ChronoPeriod twoMonthsEightDays = Symmetry454Chronology.INSTANCE.period(0, 2, 8);

        Symmetry454Date result = startDate.plus(twoMonthsEightDays);

        assertEquals(Symmetry454Date.of(2014, 8, 1), result);
    }
}
