package org.threeten.extra.chrono;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.time.LocalDate;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;

/**
 * Verifies that {@link BritishCutoverDate#from(java.time.temporal.TemporalAccessor)}
 * converts an ISO {@link LocalDate} into the equivalent British cutover date.
 */
public class TestBritishCutoverChronology_test_BritishCutoverDate_from_LocalDate {

    /**
     * Pairs of {expected British cutover date, equivalent ISO date}.
     *
     * <p>Before the 1752 cutover the British (Julian) calendar runs behind the
     * proleptic ISO (Gregorian) calendar, so the same instant has different
     * day/month/year values in each calendar. During the cutover gap of
     * 1752-09-03..1752-09-13 the British calendar leniently accepts the invalid
     * dates and maps them onto the following ISO dates.
     */
    public static Object[][] data_samples() {
        return new Object[][] {
            // Year 1: British calendar is 2 days behind ISO.
            { BritishCutoverDate.of(1, 1, 1), LocalDate.of(0, 12, 30) },
            { BritishCutoverDate.of(1, 1, 2), LocalDate.of(0, 12, 31) },
            { BritishCutoverDate.of(1, 1, 3), LocalDate.of(1, 1, 1) },
            { BritishCutoverDate.of(1, 2, 28), LocalDate.of(1, 2, 26) },
            { BritishCutoverDate.of(1, 3, 1), LocalDate.of(1, 2, 27) },
            { BritishCutoverDate.of(1, 3, 2), LocalDate.of(1, 2, 28) },
            { BritishCutoverDate.of(1, 3, 3), LocalDate.of(1, 3, 1) },

            // Year 4 is a leap year in both calendars.
            { BritishCutoverDate.of(4, 2, 28), LocalDate.of(4, 2, 26) },
            { BritishCutoverDate.of(4, 2, 29), LocalDate.of(4, 2, 27) },
            { BritishCutoverDate.of(4, 3, 1), LocalDate.of(4, 2, 28) },
            { BritishCutoverDate.of(4, 3, 2), LocalDate.of(4, 2, 29) },
            { BritishCutoverDate.of(4, 3, 3), LocalDate.of(4, 3, 1) },

            // Year 100 is a Julian leap year but not a Gregorian one, so the
            // offset grows from 2 to 3 days across February.
            { BritishCutoverDate.of(100, 2, 28), LocalDate.of(100, 2, 26) },
            { BritishCutoverDate.of(100, 2, 29), LocalDate.of(100, 2, 27) },
            { BritishCutoverDate.of(100, 3, 1), LocalDate.of(100, 2, 28) },
            { BritishCutoverDate.of(100, 3, 2), LocalDate.of(100, 3, 1) },
            { BritishCutoverDate.of(100, 3, 3), LocalDate.of(100, 3, 2) },

            // Year 0.
            { BritishCutoverDate.of(0, 12, 31), LocalDate.of(0, 12, 29) },
            { BritishCutoverDate.of(0, 12, 30), LocalDate.of(0, 12, 28) },

            // Around the 1582 papal (Gregorian) reform - unaffected in Britain.
            { BritishCutoverDate.of(1582, 10, 4), LocalDate.of(1582, 10, 14) },
            { BritishCutoverDate.of(1582, 10, 5), LocalDate.of(1582, 10, 15) },

            // Approaching the 1752 British cutover: offset is now 11 days.
            { BritishCutoverDate.of(1751, 12, 20), LocalDate.of(1751, 12, 31) },
            { BritishCutoverDate.of(1751, 12, 31), LocalDate.of(1752, 1, 11) },
            { BritishCutoverDate.of(1752, 1, 1), LocalDate.of(1752, 1, 12) },
            { BritishCutoverDate.of(1752, 9, 1), LocalDate.of(1752, 9, 12) },
            { BritishCutoverDate.of(1752, 9, 2), LocalDate.of(1752, 9, 13) },

            // The cutover gap: 1752-09-03..1752-09-13 do not exist but are
            // leniently accepted and mapped onto the following ISO dates.
            { BritishCutoverDate.of(1752, 9, 3), LocalDate.of(1752, 9, 14) },
            { BritishCutoverDate.of(1752, 9, 13), LocalDate.of(1752, 9, 24) },

            // After the cutover the two calendars are aligned again.
            { BritishCutoverDate.of(1752, 9, 14), LocalDate.of(1752, 9, 14) },
            { BritishCutoverDate.of(1945, 11, 12), LocalDate.of(1945, 11, 12) },
            { BritishCutoverDate.of(2012, 7, 5), LocalDate.of(2012, 7, 5) },
            { BritishCutoverDate.of(2012, 7, 6), LocalDate.of(2012, 7, 6) },
        };
    }

    @ParameterizedTest
    @MethodSource("data_samples")
    public void test_BritishCutoverDate_from_LocalDate(BritishCutoverDate cutover, LocalDate iso) {
        assertEquals(cutover, BritishCutoverDate.from(iso));
    }
}
