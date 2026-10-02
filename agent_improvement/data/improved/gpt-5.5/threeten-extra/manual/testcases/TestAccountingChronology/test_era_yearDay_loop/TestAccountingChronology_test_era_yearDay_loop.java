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

    private static final int FIRST_TESTED_PROLEPTIC_YEAR = -200;
    private static final int LAST_TESTED_PROLEPTIC_YEAR_EXCLUSIVE = 200;
    private static final int FIRST_DAY_OF_YEAR = 1;

    @Test
    public void test_era_yearDay_loop() {
        for (int prolepticYear = FIRST_TESTED_PROLEPTIC_YEAR;
                prolepticYear < LAST_TESTED_PROLEPTIC_YEAR_EXCLUSIVE;
                prolepticYear++) {
            AccountingDate dateFromProlepticYear = INSTANCE.dateYearDay(prolepticYear, FIRST_DAY_OF_YEAR);

            assertEquals(prolepticYear, dateFromProlepticYear.get(YEAR));

            AccountingEra expectedEra = eraFor(prolepticYear);
            assertEquals(expectedEra, dateFromProlepticYear.getEra());

            int expectedYearOfEra = yearOfEraFor(prolepticYear);
            assertEquals(expectedYearOfEra, dateFromProlepticYear.get(YEAR_OF_ERA));

            AccountingDate dateFromEraAndYearOfEra = INSTANCE.dateYearDay(
                    expectedEra,
                    expectedYearOfEra,
                    FIRST_DAY_OF_YEAR);
            assertEquals(dateFromProlepticYear, dateFromEraAndYearOfEra);
        }
    }

    private static AccountingEra eraFor(int prolepticYear) {
        return prolepticYear <= 0 ? AccountingEra.BCE : AccountingEra.CE;
    }

    private static int yearOfEraFor(int prolepticYear) {
        return prolepticYear <= 0 ? 1 - prolepticYear : prolepticYear;
    }
}
