package org.threeten.extra.chrono;

import static java.time.temporal.ChronoUnit.DAYS;
import static org.junit.jupiter.api.Assertions.assertEquals;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.Month;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;

public class TestAccountingChronology_test_minusDays {

    private static final AccountingChronology INSTANCE = new AccountingChronologyBuilder()
            .endsOn(DayOfWeek.SUNDAY)
            .nearestEndOf(Month.AUGUST)
            .withDivision(AccountingYearDivision.THIRTEEN_EVEN_MONTHS_OF_4_WEEKS)
            .leapWeekInMonth(13)
            .toChronology();

    private static final int[] DAY_OFFSETS = {0, 1, 35, -1, -60};

    public static Object[][] data_samples() {
        return new Object[][] {
                sample(1, 1, 1, 0, 9, 4),
                sample(1, 1, 2, 0, 9, 5),
                sample(1, 1, 3, 0, 9, 6),
                sample(2011, 13, 28, 2011, 8, 28),
                sample(2012, 1, 1, 2011, 8, 29),
                sample(2012, 1, 2, 2011, 8, 30),
                sample(2012, 1, 3, 2011, 8, 31),
                sample(2012, 13, 28, 2012, 8, 26),
                sample(2012, 13, 29, 2012, 8, 27),
                sample(2012, 13, 30, 2012, 8, 28),
                sample(2012, 13, 31, 2012, 8, 29),
                sample(2012, 13, 32, 2012, 8, 30),
                sample(2012, 13, 33, 2012, 8, 31),
                sample(2012, 13, 34, 2012, 9, 1),
                sample(2012, 13, 35, 2012, 9, 2),
                sample(2013, 1, 1, 2012, 9, 3),
                sample(2013, 1, 2, 2012, 9, 4),
                sample(2013, 1, 3, 2012, 9, 5),
                sample(0, 13, 35, 0, 9, 3),
                sample(0, 13, 34, 0, 9, 2),
                sample(1583, 2, 18, 1582, 10, 14),
                sample(1583, 2, 19, 1582, 10, 15),
                sample(1946, 3, 15, 1945, 11, 12),
                sample(2012, 12, 4, 2012, 7, 5),
                sample(2012, 12, 5, 2012, 7, 6)
        };
    }

    private static Object[] sample(
            int accountingYear,
            int accountingMonth,
            int accountingDay,
            int isoYear,
            int isoMonth,
            int isoDay) {

        return new Object[] {
                INSTANCE.date(accountingYear, accountingMonth, accountingDay),
                LocalDate.of(isoYear, isoMonth, isoDay)
        };
    }

    @ParameterizedTest
    @MethodSource("data_samples")
    public void test_minusDays(AccountingDate accounting, LocalDate iso) {
        for (int daysToSubtract : DAY_OFFSETS) {
            assertEquals(
                    iso.minusDays(daysToSubtract),
                    LocalDate.from(accounting.minus(daysToSubtract, DAYS)));
        }
    }
}
