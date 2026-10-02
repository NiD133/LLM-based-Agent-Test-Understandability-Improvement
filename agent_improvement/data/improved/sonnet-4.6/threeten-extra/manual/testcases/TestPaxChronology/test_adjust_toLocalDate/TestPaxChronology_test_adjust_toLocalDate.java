package org.threeten.extra.chrono;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.time.LocalDate;

import org.junit.jupiter.api.Test;

/**
 * Tests that a PaxDate can be adjusted to align with a given ISO LocalDate.
 *
 * <p>The Pax calendar aligns its epoch such that Pax CE 0001-01-01 corresponds to
 * ISO 0000-12-31. The {@code with(LocalDate)} call converts the ISO date back into
 * the equivalent PaxDate.
 */
@SuppressWarnings({"static-method"})
public class TestPaxChronology_test_adjust_toLocalDate {

    /**
     * Verifies that adjusting a PaxDate to an ISO LocalDate yields the expected PaxDate.
     *
     * <p>ISO 2012-07-06 lies in Pax month 7, day 27 of year 2012. The starting
     * PaxDate (2000-01-04) is irrelevant beyond providing the chronology context
     * required by {@code ChronoLocalDate.with(TemporalAdjuster)}.
     */
    @Test
    public void test_adjust_toLocalDate() {
        PaxDate pax = PaxDate.of(2000, 1, 4);
        PaxDate test = pax.with(LocalDate.of(2012, 7, 6));
        assertEquals(PaxDate.of(2012, 7, 27), test);
    }
}
