package org.threeten.extra.chrono;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.time.DayOfWeek;
import java.time.Month;

import org.junit.jupiter.api.Test;

public class TestAccountingChronology_test_minus_Period {

    private static final AccountingChronology ACCOUNTING_CHRONOLOGY =
            new AccountingChronologyBuilder()
                    .endsOn(DayOfWeek.SUNDAY)
                    .nearestEndOf(Month.AUGUST)
                    .withDivision(AccountingYearDivision.THIRTEEN_EVEN_MONTHS_OF_4_WEEKS)
                    .leapWeekInMonth(13)
                    .toChronology();

    private static final int START_YEAR = 2014;
    private static final int START_MONTH = 5;
    private static final int START_DAY = 26;

    private static final int PERIOD_YEARS = 0;
    private static final int PERIOD_MONTHS = 2;
    private static final int PERIOD_DAYS = 3;

    private static final int EXPECTED_YEAR = 2014;
    private static final int EXPECTED_MONTH = 3;
    private static final int EXPECTED_DAY = 23;

    @Test
    public void test_minus_Period() {
        assertEquals(
                ACCOUNTING_CHRONOLOGY.date(EXPECTED_YEAR, EXPECTED_MONTH, EXPECTED_DAY),
                ACCOUNTING_CHRONOLOGY.date(START_YEAR, START_MONTH, START_DAY)
                        .minus(ACCOUNTING_CHRONOLOGY.period(PERIOD_YEARS, PERIOD_MONTHS, PERIOD_DAYS)));
    }
}
