package org.threeten.extra.chrono;

import static java.time.temporal.ChronoUnit.DAYS;
import static org.junit.jupiter.api.Assertions.assertEquals;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.Month;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;

/**
 * Tests that {@link AccountingDate#minus(long, java.time.temporal.TemporalUnit)} with the
 * {@code DAYS} unit shifts the date by the requested number of days, staying consistent with the
 * equivalent {@link LocalDate#minusDays(long)} operation on the matching ISO date.
 */
public class TestAccountingChronology_test_minusDays {

    /**
     * Accounting calendar that ends on the Sunday nearest the end of August, divides the year into
     * thirteen 4-week months, and places the leap week in month 13.
     */
    private static final AccountingChronology INSTANCE = new AccountingChronologyBuilder()
            .endsOn(DayOfWeek.SUNDAY)
            .nearestEndOf(Month.AUGUST)
            .withDivision(AccountingYearDivision.THIRTEEN_EVEN_MONTHS_OF_4_WEEKS)
            .leapWeekInMonth(13)
            .toChronology();

    /**
     * Pairs of equivalent dates: each accounting date and the ISO date that falls on the same day.
     * Both representations must behave identically when the same number of days is subtracted.
     */
    public static Object[][] data_samples() {
        return new Object[][] {
            { INSTANCE.date(1, 1, 1), LocalDate.of(0, 9, 4) },
            { INSTANCE.date(1, 1, 2), LocalDate.of(0, 9, 5) },
            { INSTANCE.date(1, 1, 3), LocalDate.of(0, 9, 6) },
            { INSTANCE.date(2011, 13, 28), LocalDate.of(2011, 8, 28) },
            { INSTANCE.date(2012, 1, 1), LocalDate.of(2011, 8, 29) },
            { INSTANCE.date(2012, 1, 2), LocalDate.of(2011, 8, 30) },
            { INSTANCE.date(2012, 1, 3), LocalDate.of(2011, 8, 31) },
            { INSTANCE.date(2012, 13, 28), LocalDate.of(2012, 8, 26) },
            { INSTANCE.date(2012, 13, 29), LocalDate.of(2012, 8, 27) },
            { INSTANCE.date(2012, 13, 30), LocalDate.of(2012, 8, 28) },
            { INSTANCE.date(2012, 13, 31), LocalDate.of(2012, 8, 29) },
            { INSTANCE.date(2012, 13, 32), LocalDate.of(2012, 8, 30) },
            { INSTANCE.date(2012, 13, 33), LocalDate.of(2012, 8, 31) },
            { INSTANCE.date(2012, 13, 34), LocalDate.of(2012, 9, 1) },
            { INSTANCE.date(2012, 13, 35), LocalDate.of(2012, 9, 2) },
            { INSTANCE.date(2013, 1, 1), LocalDate.of(2012, 9, 3) },
            { INSTANCE.date(2013, 1, 2), LocalDate.of(2012, 9, 4) },
            { INSTANCE.date(2013, 1, 3), LocalDate.of(2012, 9, 5) },
            { INSTANCE.date(0, 13, 35), LocalDate.of(0, 9, 3) },
            { INSTANCE.date(0, 13, 34), LocalDate.of(0, 9, 2) },
            { INSTANCE.date(1583, 2, 18), LocalDate.of(1582, 10, 14) },
            { INSTANCE.date(1583, 2, 19), LocalDate.of(1582, 10, 15) },
            { INSTANCE.date(1946, 3, 15), LocalDate.of(1945, 11, 12) },
            { INSTANCE.date(2012, 12, 4), LocalDate.of(2012, 7, 5) },
            { INSTANCE.date(2012, 12, 5), LocalDate.of(2012, 7, 6) }
        };
    }

    @ParameterizedTest
    @MethodSource("data_samples")
    public void test_minusDays(AccountingDate accounting, LocalDate iso) {
        // Subtracting any number of days from the accounting date must land on the same calendar
        // day as subtracting the same amount from the equivalent ISO date.
        assertEquals(iso, LocalDate.from(accounting.minus(0, DAYS)));
        assertEquals(iso.minusDays(1), LocalDate.from(accounting.minus(1, DAYS)));
        assertEquals(iso.minusDays(35), LocalDate.from(accounting.minus(35, DAYS)));
        // Subtracting a negative amount moves the date forward.
        assertEquals(iso.minusDays(-1), LocalDate.from(accounting.minus(-1, DAYS)));
        assertEquals(iso.minusDays(-60), LocalDate.from(accounting.minus(-60, DAYS)));
    }
}
