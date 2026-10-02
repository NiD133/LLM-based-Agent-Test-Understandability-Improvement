package org.threeten.extra.chrono;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.Month;
import java.time.Period;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

/**
 * Verifies that {@link LocalDate#until(java.time.chrono.ChronoLocalDate)} returns a zero period
 * when the target accounting date denotes the very same day on the timeline as the ISO date.
 */
public class TestAccountingChronology_test_LocalDate_until_CoptiDate {

    /**
     * Accounting calendar whose year ends on the Sunday nearest the end of August, divided into
     * thirteen even four-week months, with the leap week placed in month 13.
     */
    private static final AccountingChronology CHRONOLOGY = new AccountingChronologyBuilder()
            .endsOn(DayOfWeek.SUNDAY)
            .nearestEndOf(Month.AUGUST)
            .withDivision(AccountingYearDivision.THIRTEEN_EVEN_MONTHS_OF_4_WEEKS)
            .leapWeekInMonth(13)
            .toChronology();

    /**
     * Each row pairs an accounting date with the ISO date that falls on the same calendar day.
     */
    static Arguments[] equivalentAccountingAndIsoDates() {
        return new Arguments[] {
            Arguments.of(CHRONOLOGY.date(1, 1, 1), LocalDate.of(0, 9, 4)),
            Arguments.of(CHRONOLOGY.date(1, 1, 2), LocalDate.of(0, 9, 5)),
            Arguments.of(CHRONOLOGY.date(1, 1, 3), LocalDate.of(0, 9, 6)),
            Arguments.of(CHRONOLOGY.date(2011, 13, 28), LocalDate.of(2011, 8, 28)),
            Arguments.of(CHRONOLOGY.date(2012, 1, 1), LocalDate.of(2011, 8, 29)),
            Arguments.of(CHRONOLOGY.date(2012, 1, 2), LocalDate.of(2011, 8, 30)),
            Arguments.of(CHRONOLOGY.date(2012, 1, 3), LocalDate.of(2011, 8, 31)),
            Arguments.of(CHRONOLOGY.date(2012, 13, 28), LocalDate.of(2012, 8, 26)),
            Arguments.of(CHRONOLOGY.date(2012, 13, 29), LocalDate.of(2012, 8, 27)),
            Arguments.of(CHRONOLOGY.date(2012, 13, 30), LocalDate.of(2012, 8, 28)),
            Arguments.of(CHRONOLOGY.date(2012, 13, 31), LocalDate.of(2012, 8, 29)),
            Arguments.of(CHRONOLOGY.date(2012, 13, 32), LocalDate.of(2012, 8, 30)),
            Arguments.of(CHRONOLOGY.date(2012, 13, 33), LocalDate.of(2012, 8, 31)),
            Arguments.of(CHRONOLOGY.date(2012, 13, 34), LocalDate.of(2012, 9, 1)),
            Arguments.of(CHRONOLOGY.date(2012, 13, 35), LocalDate.of(2012, 9, 2)),
            Arguments.of(CHRONOLOGY.date(2013, 1, 1), LocalDate.of(2012, 9, 3)),
            Arguments.of(CHRONOLOGY.date(2013, 1, 2), LocalDate.of(2012, 9, 4)),
            Arguments.of(CHRONOLOGY.date(2013, 1, 3), LocalDate.of(2012, 9, 5)),
            Arguments.of(CHRONOLOGY.date(0, 13, 35), LocalDate.of(0, 9, 3)),
            Arguments.of(CHRONOLOGY.date(0, 13, 34), LocalDate.of(0, 9, 2)),
            Arguments.of(CHRONOLOGY.date(1583, 2, 18), LocalDate.of(1582, 10, 14)),
            Arguments.of(CHRONOLOGY.date(1583, 2, 19), LocalDate.of(1582, 10, 15)),
            Arguments.of(CHRONOLOGY.date(1946, 3, 15), LocalDate.of(1945, 11, 12)),
            Arguments.of(CHRONOLOGY.date(2012, 12, 4), LocalDate.of(2012, 7, 5)),
            Arguments.of(CHRONOLOGY.date(2012, 12, 5), LocalDate.of(2012, 7, 6)),
        };
    }

    @ParameterizedTest
    @MethodSource("equivalentAccountingAndIsoDates")
    public void until_returnsZeroPeriod_whenIsoDateMatchesAccountingDate(AccountingDate accounting, LocalDate iso) {
        assertEquals(Period.ZERO, iso.until(accounting));
    }
}
