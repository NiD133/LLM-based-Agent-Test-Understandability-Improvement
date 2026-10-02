package org.threeten.extra.chrono;

import static java.time.temporal.ChronoField.YEAR;
import static java.time.temporal.ChronoField.YEAR_OF_ERA;
import static org.junit.jupiter.api.Assertions.assertEquals;

import java.time.DayOfWeek;
import java.time.Month;

import org.junit.jupiter.api.Test;

public class TestAccountingChronology_test_era_yearDay_loop {

    private static final AccountingChronology INSTANCE = new AccountingChronologyBuilder()
            .endsOn(DayOfWeek.SUNDAY)
            .nearestEndOf(Month.AUGUST)
            .withDivision(AccountingYearDivision.THIRTEEN_EVEN_MONTHS_OF_4_WEEKS)
            .leapWeekInMonth(13)
            .toChronology();

    /**
     * Verifies that for every proleptic year in [-200, 200), a date constructed via
     * proleptic-year/day-of-year is consistent with the same date constructed via
     * era/year-of-era/day-of-year.  Also checks that the era and year-of-era values
     * reported by the date match the expected mapping:
     * <ul>
     *   <li>year &lt;= 0  →  BCE,  yoe = 1 - year</li>
     *   <li>year &gt;  0  →  CE,   yoe = year</li>
     * </ul>
     */
    @Test
    public void test_era_yearDay_loop() {
        for (int year = -200; year < 200; year++) {
            AccountingDate base = INSTANCE.dateYearDay(year, 1);

            // Verify proleptic year round-trips correctly.
            assertEquals(year, base.get(YEAR));

            // Determine expected era and year-of-era from the proleptic year.
            AccountingEra expectedEra = (year <= 0) ? AccountingEra.BCE : AccountingEra.CE;
            int expectedYearOfEra = (year <= 0) ? 1 - year : year;

            assertEquals(expectedEra, base.getEra());
            assertEquals(expectedYearOfEra, base.get(YEAR_OF_ERA));

            // A date built from (era, year-of-era, dayOfYear) must equal the proleptic-year date.
            AccountingDate eraBased = INSTANCE.dateYearDay(expectedEra, expectedYearOfEra, 1);
            assertEquals(base, eraBased);
        }
    }
}
