package org.threeten.extra.chrono;

import static java.time.temporal.ChronoField.MINUTE_OF_DAY;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.time.DayOfWeek;
import java.time.Month;
import java.time.temporal.UnsupportedTemporalTypeException;

import org.junit.jupiter.api.Test;

public class TestAccountingChronology_test_with_TemporalField_unsupported {

    private static final AccountingChronology CHRONOLOGY = new AccountingChronologyBuilder()
            .endsOn(DayOfWeek.SUNDAY)
            .nearestEndOf(Month.AUGUST)
            .withDivision(AccountingYearDivision.THIRTEEN_EVEN_MONTHS_OF_4_WEEKS)
            .leapWeekInMonth(13)
            .toChronology();

    @Test
    public void test_with_TemporalField_unsupported() {
        AccountingDate accountingDate = CHRONOLOGY.date(2012, 6, 28);

        assertThrows(
                UnsupportedTemporalTypeException.class,
                () -> accountingDate.with(MINUTE_OF_DAY, 0));
    }
}
