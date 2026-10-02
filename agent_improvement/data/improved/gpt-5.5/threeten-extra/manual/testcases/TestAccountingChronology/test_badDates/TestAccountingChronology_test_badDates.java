package org.threeten.extra.chrono;

import static org.junit.jupiter.api.Assertions.assertThrows;

import java.time.DateTimeException;
import java.time.DayOfWeek;
import java.time.Month;
import java.util.stream.Stream;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

public class TestAccountingChronology_test_badDates {

    private static final AccountingChronology INSTANCE = new AccountingChronologyBuilder()
            .endsOn(DayOfWeek.SUNDAY)
            .nearestEndOf(Month.AUGUST)
            .withDivision(AccountingYearDivision.THIRTEEN_EVEN_MONTHS_OF_4_WEEKS)
            .leapWeekInMonth(13)
            .toChronology();

    public static Stream<Arguments> data_badDates() {
        return Stream.of(
                // Missing or out-of-range month.
                invalidDate(2012, 0, 0),
                invalidDate(2012, -1, 1),
                invalidDate(2012, 0, 1),
                invalidDate(2012, 14, 1),
                invalidDate(2012, 15, 1),

                // Missing or out-of-range day in a normal 28-day month.
                invalidDate(2012, 1, -1),
                invalidDate(2012, 1, 0),
                invalidDate(2012, 1, 29),

                // Missing or out-of-range day in the 35-day leap-week month.
                invalidDate(2012, 13, -1),
                invalidDate(2012, 13, 0),
                invalidDate(2012, 13, 36),
                invalidDate(2012, 13, 37),
                invalidDate(2012, 13, 38),

                // Month 13 only has 28 days in the non-leap accounting year 2011.
                invalidDate(2011, 13, -1),
                invalidDate(2011, 13, 0),
                invalidDate(2011, 13, 29),
                invalidDate(2011, 13, 30),
                invalidDate(2011, 13, 31),
                invalidDate(2011, 13, 32),
                invalidDate(2011, 13, 33),
                invalidDate(2011, 13, 34),
                invalidDate(2011, 13, 35),

                // All other 2012 months remain 28-day months.
                invalidDate(2012, 2, 29),
                invalidDate(2012, 3, 29),
                invalidDate(2012, 4, 29),
                invalidDate(2012, 5, 29),
                invalidDate(2012, 6, 29),
                invalidDate(2012, 7, 29),
                invalidDate(2012, 8, 29),
                invalidDate(2012, 9, 29),
                invalidDate(2012, 10, 29),
                invalidDate(2012, 11, 29),
                invalidDate(2012, 12, 29));
    }

    private static Arguments invalidDate(int year, int month, int dayOfMonth) {
        return Arguments.of(year, month, dayOfMonth);
    }

    @ParameterizedTest
    @MethodSource("data_badDates")
    public void test_badDates(int year, int month, int dom) {
        assertThrows(DateTimeException.class, () -> INSTANCE.date(year, month, dom));
    }
}
