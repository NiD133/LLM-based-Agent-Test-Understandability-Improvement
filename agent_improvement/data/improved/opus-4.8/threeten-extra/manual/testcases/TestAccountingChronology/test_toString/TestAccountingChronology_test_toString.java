package org.threeten.extra.chrono;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.time.DayOfWeek;
import java.time.Month;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;

/**
 * Tests that {@link AccountingDate#toString()} renders a human-readable description
 * of its {@link AccountingChronology}, followed by the ISO-style date.
 */
public class TestAccountingChronology_test_toString {

    /**
     * Chronology whose accounting year <em>ends</em> in the given ISO year
     * (the builder default, since {@code accountingYearStartsInIsoYear()} is not called).
     */
    private static final AccountingChronology ENDS_IN_ISO_YEAR = new AccountingChronologyBuilder()
            .endsOn(DayOfWeek.SUNDAY)
            .nearestEndOf(Month.AUGUST)
            .withDivision(AccountingYearDivision.THIRTEEN_EVEN_MONTHS_OF_4_WEEKS)
            .leapWeekInMonth(13)
            .toChronology();

    /**
     * Chronology whose accounting year <em>starts</em> in the given ISO year,
     * configured via {@code accountingYearStartsInIsoYear()}.
     */
    private static final AccountingChronology STARTS_IN_ISO_YEAR = new AccountingChronologyBuilder()
            .endsOn(DayOfWeek.SUNDAY)
            .nearestEndOf(Month.AUGUST)
            .withDivision(AccountingYearDivision.THIRTEEN_EVEN_MONTHS_OF_4_WEEKS)
            .leapWeekInMonth(13)
            .accountingYearStartsInIsoYear()
            .toChronology();

    /** Common chronology description shared by every expected string. */
    private static final String DESCRIPTION_PREFIX =
            "Accounting calendar ends on SUNDAY nearest end of AUGUST, "
            + "year divided in THIRTEEN_EVEN_MONTHS_OF_4_WEEKS with leap-week in month 13 ";

    public static Object[][] data_toString() {
        return new Object[][] {
            { ENDS_IN_ISO_YEAR.date(1, 1, 1), DESCRIPTION_PREFIX + "ending in the given ISO year CE 1-01-01" },
            { ENDS_IN_ISO_YEAR.date(2012, 6, 23), DESCRIPTION_PREFIX + "ending in the given ISO year CE 2012-06-23" },
            { STARTS_IN_ISO_YEAR.date(1, 1, 1), DESCRIPTION_PREFIX + "starting in the given ISO year CE 1-01-01" },
            { STARTS_IN_ISO_YEAR.date(2012, 6, 23), DESCRIPTION_PREFIX + "starting in the given ISO year CE 2012-06-23" },
        };
    }

    @ParameterizedTest
    @MethodSource("data_toString")
    public void test_toString(AccountingDate date, String expected) {
        assertEquals(expected, date.toString());
    }
}
