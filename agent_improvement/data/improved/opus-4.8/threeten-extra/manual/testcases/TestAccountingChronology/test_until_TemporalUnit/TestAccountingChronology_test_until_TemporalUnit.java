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

/**
 * Tests {@link AccountingDate#until(java.time.temporal.Temporal, TemporalUnit)} for the
 * accounting calendar that ends on the Sunday nearest the end of August and divides the
 * year into thirteen 4-week months (with the leap week added to month 13).
 */
public class TestAccountingChronology_test_until_TemporalUnit {

    private static final AccountingChronology INSTANCE = new AccountingChronologyBuilder()
            .endsOn(DayOfWeek.SUNDAY)
            .nearestEndOf(Month.AUGUST)
            .withDivision(AccountingYearDivision.THIRTEEN_EVEN_MONTHS_OF_4_WEEKS)
            .leapWeekInMonth(13)
            .toChronology();

    /**
     * Cases for {@link #test_until_TemporalUnit}.
     * <p>
     * Each row is: {start year, start month, start day, end year, end month, end day, unit,
     * expected amount of {@code unit} elapsed from start to end}.
     */
    public static Object[][] data_until() {
        return new Object[][] {
            // DAYS
            { 2014, 5, 26, 2014, 5, 26, DAYS, 0 },
            { 2014, 5, 26, 2014, 6, 4, DAYS, 6 },
            { 2014, 5, 26, 2014, 5, 20, DAYS, -6 },
            // WEEKS (truncated towards zero)
            { 2014, 5, 26, 2014, 5, 26, WEEKS, 0 },
            { 2014, 5, 26, 2014, 6, 4, WEEKS, 0 },
            { 2014, 5, 26, 2014, 6, 5, WEEKS, 1 },
            // MONTHS (truncated towards zero)
            { 2014, 5, 26, 2014, 5, 26, MONTHS, 0 },
            { 2014, 5, 26, 2014, 6, 25, MONTHS, 0 },
            { 2014, 5, 26, 2014, 6, 26, MONTHS, 1 },
            // YEARS (truncated towards zero)
            { 2014, 5, 26, 2014, 5, 26, YEARS, 0 },
            { 2014, 5, 26, 2015, 5, 25, YEARS, 0 },
            { 2014, 5, 26, 2015, 5, 26, YEARS, 1 },
            // DECADES (truncated towards zero)
            { 2014, 5, 26, 2014, 5, 26, DECADES, 0 },
            { 2014, 5, 26, 2024, 5, 25, DECADES, 0 },
            { 2014, 5, 26, 2024, 5, 26, DECADES, 1 },
            // CENTURIES (truncated towards zero)
            { 2014, 5, 26, 2014, 5, 26, CENTURIES, 0 },
            { 2014, 5, 26, 2114, 5, 25, CENTURIES, 0 },
            { 2014, 5, 26, 2114, 5, 26, CENTURIES, 1 },
            // MILLENNIA (truncated towards zero)
            { 2014, 5, 26, 2014, 5, 26, MILLENNIA, 0 },
            { 2014, 5, 26, 3014, 5, 25, MILLENNIA, 0 },
            { 2014, 5, 26, 3014, 5, 26, MILLENNIA, 1 },
            // ERAS (BCE year -2013 to CE)
            { -2013, 5, 26, 0, 5, 26, ERAS, 0 },
            { -2013, 5, 26, 2014, 5, 26, ERAS, 1 },
        };
    }

    @ParameterizedTest
    @MethodSource("data_until")
    public void test_until_TemporalUnit(int year1, int month1, int dom1,
            int year2, int month2, int dom2, TemporalUnit unit, long expected) {
        AccountingDate start = INSTANCE.date(year1, month1, dom1);
        AccountingDate end = INSTANCE.date(year2, month2, dom2);
        assertEquals(expected, start.until(end, unit));
    }
}
