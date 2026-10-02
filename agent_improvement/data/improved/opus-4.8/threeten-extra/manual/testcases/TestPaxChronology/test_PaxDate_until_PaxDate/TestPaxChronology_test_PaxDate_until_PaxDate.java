package org.threeten.extra.chrono;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

/**
 * Verifies that {@link PaxDate#until(java.time.chrono.ChronoLocalDate)} returns a
 * zero-length period when a date is measured against itself.
 */
@SuppressWarnings({ "static-method" })
public class TestPaxChronology_test_PaxDate_until_PaxDate {

    /**
     * A representative spread of Pax dates: the first/last days of months, the extra
     * "Pax" leap month, century boundaries and dates in the BCE (negative-year) range.
     * The exact dates are irrelevant to the assertion below - any date measured against
     * itself spans zero time - but using a varied sample guards the rule across the
     * whole supported range.
     */
    static PaxDate[] sampleDates() {
        return new PaxDate[] {
            PaxDate.of(1, 1, 1), PaxDate.of(1, 1, 2), PaxDate.of(1, 1, 3),
            PaxDate.of(1, 1, 28), PaxDate.of(1, 2, 1), PaxDate.of(1, 2, 2),
            PaxDate.of(1, 2, 3), PaxDate.of(6, 13, 6), PaxDate.of(6, 13, 7),
            PaxDate.of(6, 14, 1), PaxDate.of(6, 14, 2), PaxDate.of(6, 14, 3),
            PaxDate.of(6, 14, 27), PaxDate.of(6, 14, 28), PaxDate.of(7, 1, 1),
            PaxDate.of(7, 1, 2), PaxDate.of(399, 13, 6), PaxDate.of(399, 13, 7),
            PaxDate.of(399, 14, 1), PaxDate.of(399, 14, 2), PaxDate.of(399, 14, 3),
            PaxDate.of(400, 13, 27), PaxDate.of(400, 13, 28), PaxDate.of(401, 1, 1),
            PaxDate.of(401, 1, 2), PaxDate.of(401, 1, 3), PaxDate.of(0, 13, 28),
            PaxDate.of(0, 13, 27), PaxDate.of(1582, 10, 5), PaxDate.of(1582, 10, 6),
            PaxDate.of(1945, 10, 28), PaxDate.of(2012, 6, 23), PaxDate.of(2012, 6, 24),
            PaxDate.of(-6, 1, 1), PaxDate.of(-6, 13, 6), PaxDate.of(-6, 13, 7),
            PaxDate.of(-6, 14, 1), PaxDate.of(-6, 14, 2), PaxDate.of(-6, 14, 27),
            PaxDate.of(-6, 14, 28), PaxDate.of(-5, 1, 1), PaxDate.of(-5, 1, 2),
            PaxDate.of(-99, 1, 1), PaxDate.of(-99, 13, 6), PaxDate.of(-99, 13, 7),
            PaxDate.of(-99, 14, 1), PaxDate.of(-99, 14, 2), PaxDate.of(-100, 1, 1),
            PaxDate.of(-100, 13, 6), PaxDate.of(-100, 13, 7), PaxDate.of(-100, 14, 1),
            PaxDate.of(-100, 14, 2),
        };
    }

    @Test
    public void until_sameDate_returnsZeroPeriod() {
        for (PaxDate date : sampleDates()) {
            assertEquals(PaxChronology.INSTANCE.period(0, 0, 0), date.until(date),
                "until() of a date against itself should be a zero period: " + date);
        }
    }
}
