package org.threeten.extra.chrono;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.Month;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;

public class TestAccountingChronology_test_Chronology_date_Temporal {

    private static final AccountingChronology INSTANCE = new AccountingChronologyBuilder()
            .endsOn(DayOfWeek.SUNDAY)
            .nearestEndOf(Month.AUGUST)
            .withDivision(AccountingYearDivision.THIRTEEN_EVEN_MONTHS_OF_4_WEEKS)
            .leapWeekInMonth(13)
            .toChronology();

    public static Object[][] data_samples() {
        return new Object[][] {
                sample(INSTANCE.date(1, 1, 1), LocalDate.of(0, 9, 4)),
                sample(INSTANCE.date(1, 1, 2), LocalDate.of(0, 9, 5)),
                sample(INSTANCE.date(1, 1, 3), LocalDate.of(0, 9, 6)),

                sample(INSTANCE.date(2011, 13, 28), LocalDate.of(2011, 8, 28)),
                sample(INSTANCE.date(2012, 1, 1), LocalDate.of(2011, 8, 29)),
                sample(INSTANCE.date(2012, 1, 2), LocalDate.of(2011, 8, 30)),
                sample(INSTANCE.date(2012, 1, 3), LocalDate.of(2011, 8, 31)),

                sample(INSTANCE.date(2012, 13, 28), LocalDate.of(2012, 8, 26)),
                sample(INSTANCE.date(2012, 13, 29), LocalDate.of(2012, 8, 27)),
                sample(INSTANCE.date(2012, 13, 30), LocalDate.of(2012, 8, 28)),
                sample(INSTANCE.date(2012, 13, 31), LocalDate.of(2012, 8, 29)),
                sample(INSTANCE.date(2012, 13, 32), LocalDate.of(2012, 8, 30)),
                sample(INSTANCE.date(2012, 13, 33), LocalDate.of(2012, 8, 31)),
                sample(INSTANCE.date(2012, 13, 34), LocalDate.of(2012, 9, 1)),
                sample(INSTANCE.date(2012, 13, 35), LocalDate.of(2012, 9, 2)),

                sample(INSTANCE.date(2013, 1, 1), LocalDate.of(2012, 9, 3)),
                sample(INSTANCE.date(2013, 1, 2), LocalDate.of(2012, 9, 4)),
                sample(INSTANCE.date(2013, 1, 3), LocalDate.of(2012, 9, 5)),

                sample(INSTANCE.date(0, 13, 35), LocalDate.of(0, 9, 3)),
                sample(INSTANCE.date(0, 13, 34), LocalDate.of(0, 9, 2)),

                sample(INSTANCE.date(1583, 2, 18), LocalDate.of(1582, 10, 14)),
                sample(INSTANCE.date(1583, 2, 19), LocalDate.of(1582, 10, 15)),
                sample(INSTANCE.date(1946, 3, 15), LocalDate.of(1945, 11, 12)),

                sample(INSTANCE.date(2012, 12, 4), LocalDate.of(2012, 7, 5)),
                sample(INSTANCE.date(2012, 12, 5), LocalDate.of(2012, 7, 6))
        };
    }

    private static Object[] sample(AccountingDate accountingDate, LocalDate isoDate) {
        return new Object[] { accountingDate, isoDate };
    }

    @ParameterizedTest
    @MethodSource("data_samples")
    public void test_Chronology_date_Temporal(AccountingDate accounting, LocalDate iso) {
        assertEquals(accounting, INSTANCE.date(iso));
    }
}
