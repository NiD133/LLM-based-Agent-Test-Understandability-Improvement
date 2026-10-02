package org.threeten.extra.chrono;

import static java.time.temporal.ChronoField.ALIGNED_WEEK_OF_YEAR;
import static java.time.temporal.ChronoField.DAY_OF_MONTH;
import static java.time.temporal.ChronoField.DAY_OF_WEEK;
import static java.time.temporal.ChronoField.DAY_OF_YEAR;
import static java.time.temporal.ChronoField.MONTH_OF_YEAR;
import static org.junit.jupiter.api.Assertions.assertEquals;

import java.time.temporal.ChronoField;
import java.time.DayOfWeek;
import java.time.Month;
import java.time.temporal.ValueRange;

import org.junit.jupiter.api.Test;

public class TestAccountingChronology_test_Chronology_range {

    private static final AccountingChronology INSTANCE = new AccountingChronologyBuilder()
            .endsOn(DayOfWeek.SUNDAY)
            .nearestEndOf(Month.AUGUST)
            .withDivision(AccountingYearDivision.THIRTEEN_EVEN_MONTHS_OF_4_WEEKS)
            .leapWeekInMonth(13)
            .toChronology();

    @Test
    public void test_Chronology_range() {
        assertRange(DAY_OF_WEEK, ValueRange.of(1, 7));
        assertRange(DAY_OF_MONTH, ValueRange.of(1, 28, 35));
        assertRange(DAY_OF_YEAR, ValueRange.of(1, 364, 371));
        assertRange(MONTH_OF_YEAR, ValueRange.of(1, 13));
        assertRange(ALIGNED_WEEK_OF_YEAR, ValueRange.of(1, 52, 53));
    }

    private static void assertRange(ChronoField field, ValueRange expectedRange) {
        assertEquals(expectedRange, INSTANCE.range(field));
    }
}
