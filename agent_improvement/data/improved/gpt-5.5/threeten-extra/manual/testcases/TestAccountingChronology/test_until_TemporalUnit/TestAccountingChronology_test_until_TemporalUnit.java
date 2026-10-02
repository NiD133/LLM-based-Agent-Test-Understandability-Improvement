package org.threeten.extra.chrono;

import static java.time.temporal.ChronoUnit.CENTURIES;
import static java.time.temporal.ChronoUnit.DAYS;
import static java.time.temporal.ChronoUnit.DECADES;
import static java.time.temporal.ChronoUnit.ERAS;
import static java.time.temporal.ChronoUnit.MILLENNIA;
import static java.time.temporal.ChronoUnit.MONTHS;
import static java.time.temporal.ChronoUnit.WEEKS;
import static java.time.temporal.ChronoUnit.YEARS;
import static org.junit.jupiter.api.Assertions.assertEquals;

import java.time.DayOfWeek;
import java.time.Month;
import java.time.temporal.TemporalUnit;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;

public class TestAccountingChronology_test_until_TemporalUnit {

    private static final AccountingChronology INSTANCE = new AccountingChronologyBuilder()
            .endsOn(DayOfWeek.SUNDAY)
            .nearestEndOf(Month.AUGUST)
            .withDivision(AccountingYearDivision.THIRTEEN_EVEN_MONTHS_OF_4_WEEKS)
            .leapWeekInMonth(13)
            .toChronology();

    public static Object[][] data_until() {
        return new Object[][] {
                untilCase(2014, 5, 26, 2014, 5, 26, DAYS, 0),
                untilCase(2014, 5, 26, 2014, 6, 4, DAYS, 6),
                untilCase(2014, 5, 26, 2014, 5, 20, DAYS, -6),

                untilCase(2014, 5, 26, 2014, 5, 26, WEEKS, 0),
                untilCase(2014, 5, 26, 2014, 6, 4, WEEKS, 0),
                untilCase(2014, 5, 26, 2014, 6, 5, WEEKS, 1),

                untilCase(2014, 5, 26, 2014, 5, 26, MONTHS, 0),
                untilCase(2014, 5, 26, 2014, 6, 25, MONTHS, 0),
                untilCase(2014, 5, 26, 2014, 6, 26, MONTHS, 1),

                untilCase(2014, 5, 26, 2014, 5, 26, YEARS, 0),
                untilCase(2014, 5, 26, 2015, 5, 25, YEARS, 0),
                untilCase(2014, 5, 26, 2015, 5, 26, YEARS, 1),

                untilCase(2014, 5, 26, 2014, 5, 26, DECADES, 0),
                untilCase(2014, 5, 26, 2024, 5, 25, DECADES, 0),
                untilCase(2014, 5, 26, 2024, 5, 26, DECADES, 1),

                untilCase(2014, 5, 26, 2014, 5, 26, CENTURIES, 0),
                untilCase(2014, 5, 26, 2114, 5, 25, CENTURIES, 0),
                untilCase(2014, 5, 26, 2114, 5, 26, CENTURIES, 1),

                untilCase(2014, 5, 26, 2014, 5, 26, MILLENNIA, 0),
                untilCase(2014, 5, 26, 3014, 5, 25, MILLENNIA, 0),
                untilCase(2014, 5, 26, 3014, 5, 26, MILLENNIA, 1),

                untilCase(-2013, 5, 26, 0, 5, 26, ERAS, 0),
                untilCase(-2013, 5, 26, 2014, 5, 26, ERAS, 1),
        };
    }

    private static Object[] untilCase(
            int startYear,
            int startMonth,
            int startDayOfMonth,
            int endYear,
            int endMonth,
            int endDayOfMonth,
            TemporalUnit unit,
            long expected) {

        return new Object[] {
                startYear,
                startMonth,
                startDayOfMonth,
                endYear,
                endMonth,
                endDayOfMonth,
                unit,
                expected,
        };
    }

    @ParameterizedTest
    @MethodSource("data_until")
    public void test_until_TemporalUnit(
            int startYear,
            int startMonth,
            int startDayOfMonth,
            int endYear,
            int endMonth,
            int endDayOfMonth,
            TemporalUnit unit,
            long expected) {

        AccountingDate start = INSTANCE.date(startYear, startMonth, startDayOfMonth);
        AccountingDate end = INSTANCE.date(endYear, endMonth, endDayOfMonth);

        assertEquals(expected, start.until(end, unit));
    }
}
