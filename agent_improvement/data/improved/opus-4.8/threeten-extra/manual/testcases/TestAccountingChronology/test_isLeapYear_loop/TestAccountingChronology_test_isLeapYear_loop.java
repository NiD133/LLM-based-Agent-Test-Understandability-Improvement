package org.threeten.extra.chrono;

import static java.time.temporal.ChronoUnit.DAYS;
import static org.junit.jupiter.api.Assertions.assertEquals;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.Month;
import java.time.temporal.TemporalAdjusters;
import java.util.function.IntPredicate;

import org.junit.jupiter.api.Test;

public class TestAccountingChronology_test_isLeapYear_loop {

    /**
     * The chronology under test: an accounting calendar whose year ends on the
     * Sunday nearest the end of August, split into 13 four-week months, with the
     * leap week added to month 13.
     */
    private static final AccountingChronology INSTANCE = new AccountingChronologyBuilder()
            .endsOn(DayOfWeek.SUNDAY)
            .nearestEndOf(Month.AUGUST)
            .withDivision(AccountingYearDivision.THIRTEEN_EVEN_MONTHS_OF_4_WEEKS)
            .leapWeekInMonth(13)
            .toChronology();

    /**
     * Independent reference definition of a leap year for this calendar: a year
     * is a leap year exactly when it spans 371 days (53 weeks) instead of the
     * usual 364 days (52 weeks). The span is measured between the end of the
     * previous accounting year and the end of the current one, each of which is
     * the Sunday on or before September 3rd of the corresponding ISO year.
     */
    private static final IntPredicate REFERENCE_IS_LEAP_YEAR = year -> {
        LocalDate currentYearEnd = LocalDate.of(year, 9, 3)
                .with(TemporalAdjusters.previousOrSame(DayOfWeek.SUNDAY));
        LocalDate previousYearEnd = LocalDate.of(year - 1, 9, 3)
                .with(TemporalAdjusters.previousOrSame(DayOfWeek.SUNDAY));
        return previousYearEnd.until(currentYearEnd, DAYS) == 371;
    };

    @Test
    public void test_isLeapYear_loop() {
        for (int year = -200; year < 200; year++) {
            boolean expectedLeapYear = REFERENCE_IS_LEAP_YEAR.test(year);

            // The first day of the accounting year must agree with the reference.
            AccountingDate firstDayOfYear = INSTANCE.date(year, 1, 1);
            assertEquals(expectedLeapYear, firstDayOfYear.isLeapYear());

            // The chronology itself must agree with the reference.
            assertEquals(expectedLeapYear, INSTANCE.isLeapYear(year));
        }
    }
}
