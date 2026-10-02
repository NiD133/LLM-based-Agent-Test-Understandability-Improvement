package org.threeten.extra.chrono;

import static java.time.temporal.ChronoField.YEAR;
import static java.time.temporal.ChronoField.YEAR_OF_ERA;
import static org.junit.jupiter.api.Assertions.assertEquals;

import java.time.DayOfWeek;
import java.time.Month;

import org.junit.jupiter.api.Test;

public class TestAccountingChronology_test_era_loop {

    private static final AccountingChronology INSTANCE = new AccountingChronologyBuilder()
            .endsOn(DayOfWeek.SUNDAY)
            .nearestEndOf(Month.AUGUST)
            .withDivision(AccountingYearDivision.THIRTEEN_EVEN_MONTHS_OF_4_WEEKS)
            .leapWeekInMonth(13)
            .toChronology();

    @Test
    public void test_era_loop() {
        for (int year = -200; year < 200; year++) {
            AccountingDate base = INSTANCE.date(year, 1, 1);
            AccountingEra expectedEra = expectedEra(year);
            int expectedYearOfEra = expectedYearOfEra(year);

            assertEquals(year, base.get(YEAR));
            assertEquals(expectedEra, base.getEra());
            assertEquals(expectedYearOfEra, base.get(YEAR_OF_ERA));

            AccountingDate eraBased = INSTANCE.date(expectedEra, expectedYearOfEra, 1, 1);
            assertEquals(base, eraBased);
        }
    }

    private static AccountingEra expectedEra(int prolepticYear) {
        return prolepticYear <= 0 ? AccountingEra.BCE : AccountingEra.CE;
    }

    private static int expectedYearOfEra(int prolepticYear) {
        return prolepticYear <= 0 ? 1 - prolepticYear : prolepticYear;
    }
}
