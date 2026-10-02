package org.threeten.extra.chrono;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.Month;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import java.util.stream.Stream;

/**
 * Verifies that converting an {@link AccountingDate} to an ISO {@link LocalDate}
 * via {@link LocalDate#from} yields the expected calendar date.
 * <p>
 * The chronology under test uses an accounting year that ends on the Sunday
 * nearest the end of August, split into thirteen 4-week months, with the
 * leap-week placed in month 13.
 */
public class TestAccountingChronology_test_LocalDate_from_AccountingDate {

    private static final AccountingChronology ACCOUNTING_CHRONOLOGY = new AccountingChronologyBuilder()
            .endsOn(DayOfWeek.SUNDAY)
            .nearestEndOf(Month.AUGUST)
            .withDivision(AccountingYearDivision.THIRTEEN_EVEN_MONTHS_OF_4_WEEKS)
            .leapWeekInMonth(13)
            .toChronology();

    /**
     * Each case pairs an accounting date (proleptic-year, month, day-of-month)
     * with the ISO date it should convert to.
     */
    static Stream<Arguments> accountingDateToIsoDate() {
        return Stream.of(
                // first days of the very first accounting year
                Arguments.of(accountingDate(1, 1, 1), LocalDate.of(0, 9, 4)),
                Arguments.of(accountingDate(1, 1, 2), LocalDate.of(0, 9, 5)),
                Arguments.of(accountingDate(1, 1, 3), LocalDate.of(0, 9, 6)),
                // boundary between accounting years 2011 and 2012
                Arguments.of(accountingDate(2011, 13, 28), LocalDate.of(2011, 8, 28)),
                Arguments.of(accountingDate(2012, 1, 1), LocalDate.of(2011, 8, 29)),
                Arguments.of(accountingDate(2012, 1, 2), LocalDate.of(2011, 8, 30)),
                Arguments.of(accountingDate(2012, 1, 3), LocalDate.of(2011, 8, 31)),
                // the leap-week stretch of month 13 in the leap year 2012
                Arguments.of(accountingDate(2012, 13, 28), LocalDate.of(2012, 8, 26)),
                Arguments.of(accountingDate(2012, 13, 29), LocalDate.of(2012, 8, 27)),
                Arguments.of(accountingDate(2012, 13, 30), LocalDate.of(2012, 8, 28)),
                Arguments.of(accountingDate(2012, 13, 31), LocalDate.of(2012, 8, 29)),
                Arguments.of(accountingDate(2012, 13, 32), LocalDate.of(2012, 8, 30)),
                Arguments.of(accountingDate(2012, 13, 33), LocalDate.of(2012, 8, 31)),
                Arguments.of(accountingDate(2012, 13, 34), LocalDate.of(2012, 9, 1)),
                Arguments.of(accountingDate(2012, 13, 35), LocalDate.of(2012, 9, 2)),
                // start of accounting year 2013
                Arguments.of(accountingDate(2013, 1, 1), LocalDate.of(2012, 9, 3)),
                Arguments.of(accountingDate(2013, 1, 2), LocalDate.of(2012, 9, 4)),
                Arguments.of(accountingDate(2013, 1, 3), LocalDate.of(2012, 9, 5)),
                // late days of accounting year 0
                Arguments.of(accountingDate(0, 13, 35), LocalDate.of(0, 9, 3)),
                Arguments.of(accountingDate(0, 13, 34), LocalDate.of(0, 9, 2)),
                // assorted historical dates
                Arguments.of(accountingDate(1583, 2, 18), LocalDate.of(1582, 10, 14)),
                Arguments.of(accountingDate(1583, 2, 19), LocalDate.of(1582, 10, 15)),
                Arguments.of(accountingDate(1946, 3, 15), LocalDate.of(1945, 11, 12)),
                Arguments.of(accountingDate(2012, 12, 4), LocalDate.of(2012, 7, 5)),
                Arguments.of(accountingDate(2012, 12, 5), LocalDate.of(2012, 7, 6)));
    }

    private static AccountingDate accountingDate(int prolepticYear, int month, int dayOfMonth) {
        return ACCOUNTING_CHRONOLOGY.date(prolepticYear, month, dayOfMonth);
    }

    @ParameterizedTest
    @MethodSource("accountingDateToIsoDate")
    public void test_LocalDate_from_AccountingDate(AccountingDate accounting, LocalDate expectedIso) {
        assertEquals(expectedIso, LocalDate.from(accounting));
    }
}
