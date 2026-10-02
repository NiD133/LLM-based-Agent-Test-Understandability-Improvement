package org.threeten.extra.chrono;

import java.time.DayOfWeek;
import java.time.Month;

import org.junit.jupiter.api.Test;

import com.google.common.testing.EqualsTester;

public class TestAccountingChronology_test_equals_and_hashCode {

    private static final AccountingChronology INSTANCE = new AccountingChronologyBuilder()
            .endsOn(DayOfWeek.SUNDAY)
            .nearestEndOf(Month.AUGUST)
            .withDivision(AccountingYearDivision.THIRTEEN_EVEN_MONTHS_OF_4_WEEKS)
            .leapWeekInMonth(13)
            .toChronology();

    @Test
    public void test_equals_and_hashCode() {
        AccountingChronology other = new AccountingChronologyBuilder()
                .endsOn(DayOfWeek.WEDNESDAY)
                .nearestEndOf(Month.AUGUST)
                .leapWeekInMonth(13)
                .withDivision(AccountingYearDivision.THIRTEEN_EVEN_MONTHS_OF_4_WEEKS)
                .accountingYearEndsInIsoYear()
                .toChronology();

        AccountingDate sameDate = INSTANCE.date(2000, 1, 3);
        AccountingDate nextDay = INSTANCE.date(2000, 1, 4);
        AccountingDate nextMonth = INSTANCE.date(2000, 2, 3);
        AccountingDate nextYear = INSTANCE.date(2001, 1, 3);
        AccountingDate sameFieldsDifferentChronology = other.date(2000, 1, 3);

        new EqualsTester()
                .addEqualityGroup(sameDate, INSTANCE.date(2000, 1, 3))
                .addEqualityGroup(nextDay, INSTANCE.date(2000, 1, 4))
                .addEqualityGroup(nextMonth, INSTANCE.date(2000, 2, 3))
                .addEqualityGroup(nextYear, INSTANCE.date(2001, 1, 3))
                .addEqualityGroup(sameFieldsDifferentChronology, other.date(2000, 1, 3))
                .testEquals();
    }
}
