package org.threeten.extra.chrono;

import static java.time.temporal.ChronoUnit.DAYS;
import static org.junit.jupiter.api.Assertions.assertEquals;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.Month;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;

/**
 * Verifies that adding days to an {@link AccountingDate} stays in step with the
 * equivalent ISO date.
 * <p>
 * Each test case pairs an accounting date with the ISO {@link LocalDate} that
 * falls on the same day. Adding any number of days to the accounting date should
 * land on exactly the same day as adding that number of days to its ISO twin.
 */
public class TestAccountingChronology_test_plusDays {

    /**
     * The calendar under test: an accounting year that ends on the Sunday nearest
     * the end of August, split into thirteen 4-week months, with the leap week
     * placed in month 13.
     */
    private static final AccountingChronology ACCOUNTING_CHRONOLOGY =
            new AccountingChronologyBuilder()
                    .endsOn(DayOfWeek.SUNDAY)
                    .nearestEndOf(Month.AUGUST)
                    .withDivision(AccountingYearDivision.THIRTEEN_EVEN_MONTHS_OF_4_WEEKS)
                    .leapWeekInMonth(13)
                    .toChronology();

    /** Day offsets exercised against every sample date (including zero, forward and backward). */
    private static final int[] DAY_OFFSETS = { 0, 1, 35, -1, -60 };

    /**
     * Each row maps an accounting date to the ISO date that represents the same calendar day.
     */
    public static Object[][] data_samples() {
        return new Object[][] {
            { ACCOUNTING_CHRONOLOGY.date(1, 1, 1), LocalDate.of(0, 9, 4) },
            { ACCOUNTING_CHRONOLOGY.date(1, 1, 2), LocalDate.of(0, 9, 5) },
            { ACCOUNTING_CHRONOLOGY.date(1, 1, 3), LocalDate.of(0, 9, 6) },
            { ACCOUNTING_CHRONOLOGY.date(2011, 13, 28), LocalDate.of(2011, 8, 28) },
            { ACCOUNTING_CHRONOLOGY.date(2012, 1, 1), LocalDate.of(2011, 8, 29) },
            { ACCOUNTING_CHRONOLOGY.date(2012, 1, 2), LocalDate.of(2011, 8, 30) },
            { ACCOUNTING_CHRONOLOGY.date(2012, 1, 3), LocalDate.of(2011, 8, 31) },
            { ACCOUNTING_CHRONOLOGY.date(2012, 13, 28), LocalDate.of(2012, 8, 26) },
            { ACCOUNTING_CHRONOLOGY.date(2012, 13, 29), LocalDate.of(2012, 8, 27) },
            { ACCOUNTING_CHRONOLOGY.date(2012, 13, 30), LocalDate.of(2012, 8, 28) },
            { ACCOUNTING_CHRONOLOGY.date(2012, 13, 31), LocalDate.of(2012, 8, 29) },
            { ACCOUNTING_CHRONOLOGY.date(2012, 13, 32), LocalDate.of(2012, 8, 30) },
            { ACCOUNTING_CHRONOLOGY.date(2012, 13, 33), LocalDate.of(2012, 8, 31) },
            { ACCOUNTING_CHRONOLOGY.date(2012, 13, 34), LocalDate.of(2012, 9, 1) },
            { ACCOUNTING_CHRONOLOGY.date(2012, 13, 35), LocalDate.of(2012, 9, 2) },
            { ACCOUNTING_CHRONOLOGY.date(2013, 1, 1), LocalDate.of(2012, 9, 3) },
            { ACCOUNTING_CHRONOLOGY.date(2013, 1, 2), LocalDate.of(2012, 9, 4) },
            { ACCOUNTING_CHRONOLOGY.date(2013, 1, 3), LocalDate.of(2012, 9, 5) },
            { ACCOUNTING_CHRONOLOGY.date(0, 13, 35), LocalDate.of(0, 9, 3) },
            { ACCOUNTING_CHRONOLOGY.date(0, 13, 34), LocalDate.of(0, 9, 2) },
            { ACCOUNTING_CHRONOLOGY.date(1583, 2, 18), LocalDate.of(1582, 10, 14) },
            { ACCOUNTING_CHRONOLOGY.date(1583, 2, 19), LocalDate.of(1582, 10, 15) },
            { ACCOUNTING_CHRONOLOGY.date(1946, 3, 15), LocalDate.of(1945, 11, 12) },
            { ACCOUNTING_CHRONOLOGY.date(2012, 12, 4), LocalDate.of(2012, 7, 5) },
            { ACCOUNTING_CHRONOLOGY.date(2012, 12, 5), LocalDate.of(2012, 7, 6) }
        };
    }

    @ParameterizedTest
    @MethodSource("data_samples")
    public void test_plusDays(AccountingDate accountingDate, LocalDate equivalentIsoDate) {
        for (int offset : DAY_OFFSETS) {
            LocalDate expected = equivalentIsoDate.plusDays(offset);
            LocalDate actual = LocalDate.from(accountingDate.plus(offset, DAYS));
            assertEquals(expected, actual);
        }
    }
}
