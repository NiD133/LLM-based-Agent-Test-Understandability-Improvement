package org.threeten.extra.chrono;

import static java.time.temporal.ChronoField.YEAR;
import static java.time.temporal.ChronoField.YEAR_OF_ERA;
import static org.junit.jupiter.api.Assertions.assertEquals;

import java.time.DayOfWeek;
import java.time.Month;

import org.junit.jupiter.api.Test;

/**
 * Verifies that {@link AccountingChronology} models eras consistently across a
 * span of proleptic years.
 *
 * <p>For each year the test checks four related facts:
 * <ul>
 *   <li>the proleptic {@code YEAR} field round-trips,</li>
 *   <li>years {@code <= 0} fall in the BCE era and positive years in the CE era,</li>
 *   <li>the {@code YEAR_OF_ERA} field follows the proleptic-to-era mapping
 *       ({@code 1 - year} for BCE, {@code year} for CE), and</li>
 *   <li>constructing a date from its (era, year-of-era) pair yields the same
 *       date as constructing it from the proleptic year.</li>
 * </ul>
 */
public class TestAccountingChronology_test_era_loop {

    private static final AccountingChronology INSTANCE = new AccountingChronologyBuilder()
            .endsOn(DayOfWeek.SUNDAY)
            .nearestEndOf(Month.AUGUST)
            .withDivision(AccountingYearDivision.THIRTEEN_EVEN_MONTHS_OF_4_WEEKS)
            .leapWeekInMonth(13)
            .toChronology();

    @Test
    public void test_era_loop() {
        for (int prolepticYear = -200; prolepticYear < 200; prolepticYear++) {
            AccountingDate dateByProlepticYear = INSTANCE.date(prolepticYear, 1, 1);

            // The proleptic year is preserved as the YEAR field.
            assertEquals(prolepticYear, dateByProlepticYear.get(YEAR));

            // Non-positive years are BCE; positive years are CE.
            AccountingEra expectedEra = (prolepticYear <= 0) ? AccountingEra.BCE : AccountingEra.CE;
            assertEquals(expectedEra, dateByProlepticYear.getEra());

            // Year-of-era counts backwards through the BCE era (1 - year),
            // and matches the proleptic year for CE.
            int expectedYearOfEra = (prolepticYear <= 0) ? 1 - prolepticYear : prolepticYear;
            assertEquals(expectedYearOfEra, dateByProlepticYear.get(YEAR_OF_ERA));

            // Building the date from (era, year-of-era) must equal the proleptic-year date.
            AccountingDate dateByEra = INSTANCE.date(expectedEra, expectedYearOfEra, 1, 1);
            assertEquals(dateByProlepticYear, dateByEra);
        }
    }
}
