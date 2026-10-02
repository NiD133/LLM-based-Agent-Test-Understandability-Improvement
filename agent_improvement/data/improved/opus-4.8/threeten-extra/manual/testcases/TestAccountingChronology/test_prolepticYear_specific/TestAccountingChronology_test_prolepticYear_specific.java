package org.threeten.extra.chrono;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.time.DayOfWeek;
import java.time.Month;

import org.junit.jupiter.api.Test;

/**
 * Verifies how {@link AccountingChronology#prolepticYear(java.time.chrono.Era, int)}
 * maps an era and year-of-era onto a single proleptic-year line.
 * <p>
 * The expected mapping is:
 * <ul>
 *   <li>For the current era (CE), the proleptic-year equals the year-of-era.</li>
 *   <li>For the previous era (BCE), the proleptic-year is {@code 1 - yearOfEra},
 *       so BCE year 1 is proleptic-year 0, BCE year 2 is -1, and so on.</li>
 * </ul>
 */
public class TestAccountingChronology_test_prolepticYear_specific {

    // The concrete configuration is irrelevant to prolepticYear(), which only
    // depends on the era and year-of-era; any AccountingChronology behaves the same.
    private static final AccountingChronology INSTANCE = new AccountingChronologyBuilder()
            .endsOn(DayOfWeek.SUNDAY)
            .nearestEndOf(Month.AUGUST)
            .withDivision(AccountingYearDivision.THIRTEEN_EVEN_MONTHS_OF_4_WEEKS)
            .leapWeekInMonth(13)
            .toChronology();

    @Test
    public void test_prolepticYear_specific() {
        // Current era: proleptic-year mirrors the year-of-era.
        assertEquals(4, INSTANCE.prolepticYear(AccountingEra.CE, 4));
        assertEquals(3, INSTANCE.prolepticYear(AccountingEra.CE, 3));
        assertEquals(2, INSTANCE.prolepticYear(AccountingEra.CE, 2));
        assertEquals(1, INSTANCE.prolepticYear(AccountingEra.CE, 1));

        // Previous era: proleptic-year counts down through zero (1 - yearOfEra).
        assertEquals(0, INSTANCE.prolepticYear(AccountingEra.BCE, 1));
        assertEquals(-1, INSTANCE.prolepticYear(AccountingEra.BCE, 2));
        assertEquals(-2, INSTANCE.prolepticYear(AccountingEra.BCE, 3));
        assertEquals(-3, INSTANCE.prolepticYear(AccountingEra.BCE, 4));
    }
}
