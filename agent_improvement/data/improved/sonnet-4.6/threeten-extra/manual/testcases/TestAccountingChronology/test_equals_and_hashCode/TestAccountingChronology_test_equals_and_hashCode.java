package org.threeten.extra.chrono;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.time.DayOfWeek;
import java.time.Month;

import org.junit.jupiter.api.Test;

import com.google.common.testing.EqualsTester;

public class TestAccountingChronology_test_equals_and_hashCode {

    // Chronology: year ends on Sunday nearest end of August,
    // divided into 13 even months of 4 weeks, leap week in month 13,
    // accounting year ends in the corresponding ISO year.
    private static final AccountingChronology INSTANCE = new AccountingChronologyBuilder()
            .endsOn(DayOfWeek.SUNDAY)
            .nearestEndOf(Month.AUGUST)
            .withDivision(AccountingYearDivision.THIRTEEN_EVEN_MONTHS_OF_4_WEEKS)
            .leapWeekInMonth(13)
            .toChronology();

    @Test
    public void test_equals_and_hashCode() {
        // A chronology that differs only in the end-of-week day (WEDNESDAY vs SUNDAY).
        AccountingChronology other = new AccountingChronologyBuilder()
                .endsOn(DayOfWeek.WEDNESDAY)
                .nearestEndOf(Month.AUGUST)
                .leapWeekInMonth(13)
                .withDivision(AccountingYearDivision.THIRTEEN_EVEN_MONTHS_OF_4_WEEKS)
                .accountingYearEndsInIsoYear()
                .toChronology();

        new EqualsTester()
                // Same date, same chronology — must be equal to each other.
                .addEqualityGroup(INSTANCE.date(2000, 1, 3), INSTANCE.date(2000, 1, 3))
                // Same year/month, different day — must be unequal to the previous group.
                .addEqualityGroup(INSTANCE.date(2000, 1, 4), INSTANCE.date(2000, 1, 4))
                // Different month within the same year.
                .addEqualityGroup(INSTANCE.date(2000, 2, 3), INSTANCE.date(2000, 2, 3))
                // Different year.
                .addEqualityGroup(INSTANCE.date(2001, 1, 3), INSTANCE.date(2001, 1, 3))
                // Same date fields but a different chronology instance — must be unequal.
                .addEqualityGroup(other.date(2000, 1, 3), other.date(2000, 1, 3))
                .testEquals();
    }
}
