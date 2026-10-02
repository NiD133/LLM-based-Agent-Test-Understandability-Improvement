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
     * For every proleptic year in a wide range, a date built directly from the proleptic
     * year must be identical to the same date rebuilt from its (era, year-of-era) pair.
     * Years &lt;= 0 fall in the BCE era (year-of-era = 1 - year); years &gt; 0 fall in CE
     * (year-of-era = year).
     */
    @Test
    public void test_era_yearDay_loop() {
        for (int prolepticYear = -200; prolepticYear < 200; prolepticYear++) {
            AccountingDate dateFromProlepticYear = INSTANCE.dateYearDay(prolepticYear, 1);
            assertEquals(prolepticYear, dateFromProlepticYear.get(YEAR));

            AccountingEra expectedEra = (prolepticYear <= 0 ? AccountingEra.BCE : AccountingEra.CE);
            assertEquals(expectedEra, dateFromProlepticYear.getEra());

            int expectedYearOfEra = (prolepticYear <= 0 ? 1 - prolepticYear : prolepticYear);
            assertEquals(expectedYearOfEra, dateFromProlepticYear.get(YEAR_OF_ERA));

            AccountingDate dateFromEraAndYearOfEra = INSTANCE.dateYearDay(expectedEra, expectedYearOfEra, 1);
            assertEquals(dateFromProlepticYear, dateFromEraAndYearOfEra);
        }
    }
}
