package org.threeten.extra.chrono;

import static java.time.temporal.ChronoUnit.DAYS;
import static org.junit.jupiter.api.Assertions.assertEquals;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.Month;
import java.time.temporal.TemporalAdjusters;
import org.junit.jupiter.api.Test;

public class TestAccountingChronology_test_isLeapYear_loop {

    // Calendar configured to end on the Sunday nearest to the end of August,
    // divided into 13 even months of 4 weeks, with the leap week in month 13.
    private static final AccountingChronology INSTANCE = new AccountingChronologyBuilder()
            .endsOn(DayOfWeek.SUNDAY)
            .nearestEndOf(Month.AUGUST)
            .withDivision(AccountingYearDivision.THIRTEEN_EVEN_MONTHS_OF_4_WEEKS)
            .leapWeekInMonth(13)
            .toChronology();

    // An accounting leap year has 53 weeks (371 days) instead of the standard 52 weeks (364 days).
    private static final int ACCOUNTING_LEAP_YEAR_DAYS = 371;

    // The year ends on the nearest Sunday to August 31. That Sunday can fall as late
    // as September 3rd (3 days after August 31), so September 3 is used as the anchor.
    private static final int YEAR_END_ANCHOR_MONTH = 9;
    private static final int YEAR_END_ANCHOR_DAY = 3;

    /**
     * Returns the accounting year-end date for the given ISO year: the Sunday on or
     * before September 3rd, which is the nearest Sunday to the end of August.
     */
    private static LocalDate accountingYearEnd(int isoYear) {
        return LocalDate.of(isoYear, YEAR_END_ANCHOR_MONTH, YEAR_END_ANCHOR_DAY)
                .with(TemporalAdjusters.previousOrSame(DayOfWeek.SUNDAY));
    }

    /**
     * Returns whether the given accounting year is a leap year, by checking whether
     * it spans 371 days (the gap between two consecutive year-end Sundays).
     */
    private static boolean isAccountingLeapYear(int year) {
        LocalDate currentYearEnd = accountingYearEnd(year);
        LocalDate prevYearEnd = accountingYearEnd(year - 1);
        return prevYearEnd.until(currentYearEnd, DAYS) == ACCOUNTING_LEAP_YEAR_DAYS;
    }

    @Test
    public void test_isLeapYear_loop() {
        for (int year = -200; year < 200; year++) {
            boolean expectedLeap = isAccountingLeapYear(year);
            AccountingDate date = INSTANCE.date(year, 1, 1);
            assertEquals(expectedLeap, date.isLeapYear(),
                    "AccountingDate.isLeapYear() mismatch for year " + year);
            assertEquals(expectedLeap, INSTANCE.isLeapYear(year),
                    "AccountingChronology.isLeapYear() mismatch for year " + year);
        }
    }
}
