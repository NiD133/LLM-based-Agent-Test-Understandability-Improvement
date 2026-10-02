package org.threeten.extra.chrono;

import static java.time.temporal.ChronoUnit.DAYS;
import static org.junit.jupiter.api.Assertions.assertEquals;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.Month;
import java.time.temporal.TemporalAdjusters;

import org.junit.jupiter.api.Test;

public class TestAccountingChronology_test_isLeapYear_loop {

    private static final AccountingChronology INSTANCE = new AccountingChronologyBuilder()
            .endsOn(DayOfWeek.SUNDAY)
            .nearestEndOf(Month.AUGUST)
            .withDivision(AccountingYearDivision.THIRTEEN_EVEN_MONTHS_OF_4_WEEKS)
            .leapWeekInMonth(13)
            .toChronology();

    private static final int FIRST_TEST_YEAR = -200;
    private static final int YEAR_AFTER_LAST_TEST_YEAR = 200;
    private static final int LEAP_ACCOUNTING_YEAR_LENGTH_IN_DAYS = 371;

    @Test
    public void test_isLeapYear_loop() {
        for (int year = FIRST_TEST_YEAR; year < YEAR_AFTER_LAST_TEST_YEAR; year++) {
            AccountingDate firstDayOfAccountingYear = INSTANCE.date(year, 1, 1);
            boolean expectedLeapYear = isExpectedLeapYear(year);

            assertEquals(expectedLeapYear, firstDayOfAccountingYear.isLeapYear());
            assertEquals(expectedLeapYear, INSTANCE.isLeapYear(year));
        }
    }

    private static boolean isExpectedLeapYear(int year) {
        LocalDate currentYearEnd = accountingYearEndInIsoYear(year);
        LocalDate previousYearEnd = accountingYearEndInIsoYear(year - 1);
        return previousYearEnd.until(currentYearEnd, DAYS) == LEAP_ACCOUNTING_YEAR_LENGTH_IN_DAYS;
    }

    private static LocalDate accountingYearEndInIsoYear(int year) {
        return LocalDate.of(year, 9, 3).with(TemporalAdjusters.previousOrSame(DayOfWeek.SUNDAY));
    }
}
