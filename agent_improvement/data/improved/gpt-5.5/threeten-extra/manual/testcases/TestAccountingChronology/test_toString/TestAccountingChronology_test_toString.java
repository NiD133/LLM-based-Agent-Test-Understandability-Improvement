package org.threeten.extra.chrono;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.time.DayOfWeek;
import java.time.Month;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;

public class TestAccountingChronology_test_toString {

    private static final String CHRONOLOGY_DESCRIPTION =
            "Accounting calendar ends on SUNDAY nearest end of AUGUST, " +
            "year divided in THIRTEEN_EVEN_MONTHS_OF_4_WEEKS " +
            "with leap-week in month 13 ";

    private static final AccountingChronology INSTANCE = new AccountingChronologyBuilder()
            .endsOn(DayOfWeek.SUNDAY)
            .nearestEndOf(Month.AUGUST)
            .withDivision(AccountingYearDivision.THIRTEEN_EVEN_MONTHS_OF_4_WEEKS)
            .leapWeekInMonth(13)
            .toChronology();

    public static Object[][] data_toString() {
        AccountingChronology startingInIsoYear = new AccountingChronologyBuilder()
                .endsOn(DayOfWeek.SUNDAY)
                .nearestEndOf(Month.AUGUST)
                .withDivision(AccountingYearDivision.THIRTEEN_EVEN_MONTHS_OF_4_WEEKS)
                .leapWeekInMonth(13)
                .accountingYearStartsInIsoYear()
                .toChronology();

        return new Object[][] {
                {
                        INSTANCE.date(1, 1, 1),
                        CHRONOLOGY_DESCRIPTION + "ending in the given ISO year CE 1-01-01"
                },
                {
                        INSTANCE.date(2012, 6, 23),
                        CHRONOLOGY_DESCRIPTION + "ending in the given ISO year CE 2012-06-23"
                },
                {
                        startingInIsoYear.date(1, 1, 1),
                        CHRONOLOGY_DESCRIPTION + "starting in the given ISO year CE 1-01-01"
                },
                {
                        startingInIsoYear.date(2012, 6, 23),
                        CHRONOLOGY_DESCRIPTION + "starting in the given ISO year CE 2012-06-23"
                }
        };
    }

    @ParameterizedTest
    @MethodSource("data_toString")
    public void test_toString(AccountingDate accounting, String expected) {
        assertEquals(expected, accounting.toString());
    }
}
