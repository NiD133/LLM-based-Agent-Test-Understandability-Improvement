package org.threeten.extra.chrono;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.time.DayOfWeek;
import java.time.Month;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;

public class TestAccountingChronology_test_toString {

    // Accounting chronology whose year ends in the ISO year it falls in (year-end convention)
    private static final AccountingChronology INSTANCE =
            new AccountingChronologyBuilder()
                    .endsOn(DayOfWeek.SUNDAY)
                    .nearestEndOf(Month.AUGUST)
                    .withDivision(AccountingYearDivision.THIRTEEN_EVEN_MONTHS_OF_4_WEEKS)
                    .leapWeekInMonth(13)
                    .toChronology();

    // Accounting chronology whose year starts in the ISO year it falls in (year-start convention)
    private static final AccountingChronology INSTANCE_YEAR_STARTS =
            new AccountingChronologyBuilder()
                    .endsOn(DayOfWeek.SUNDAY)
                    .nearestEndOf(Month.AUGUST)
                    .withDivision(AccountingYearDivision.THIRTEEN_EVEN_MONTHS_OF_4_WEEKS)
                    .leapWeekInMonth(13)
                    .accountingYearStartsInIsoYear()
                    .toChronology();

    private static final String ENDS_IN_ISO_YEAR_PREFIX =
            "Accounting calendar ends on SUNDAY nearest end of AUGUST, " +
            "year divided in THIRTEEN_EVEN_MONTHS_OF_4_WEEKS with leap-week in month 13 " +
            "ending in the given ISO year ";

    private static final String STARTS_IN_ISO_YEAR_PREFIX =
            "Accounting calendar ends on SUNDAY nearest end of AUGUST, " +
            "year divided in THIRTEEN_EVEN_MONTHS_OF_4_WEEKS with leap-week in month 13 " +
            "starting in the given ISO year ";

    public static Object[][] data_toString() {
        return new Object[][] {
            // year-end convention: accounting year "ends in" the named ISO year
            { INSTANCE.date(1, 1, 1),     ENDS_IN_ISO_YEAR_PREFIX + "CE 1-01-01" },
            { INSTANCE.date(2012, 6, 23),  ENDS_IN_ISO_YEAR_PREFIX + "CE 2012-06-23" },
            // year-start convention: accounting year "starts in" the named ISO year
            { INSTANCE_YEAR_STARTS.date(1, 1, 1),     STARTS_IN_ISO_YEAR_PREFIX + "CE 1-01-01" },
            { INSTANCE_YEAR_STARTS.date(2012, 6, 23),  STARTS_IN_ISO_YEAR_PREFIX + "CE 2012-06-23" },
        };
    }

    @ParameterizedTest
    @MethodSource("data_toString")
    public void test_toString(AccountingDate accounting, String expected) {
        assertEquals(expected, accounting.toString());
    }
}
