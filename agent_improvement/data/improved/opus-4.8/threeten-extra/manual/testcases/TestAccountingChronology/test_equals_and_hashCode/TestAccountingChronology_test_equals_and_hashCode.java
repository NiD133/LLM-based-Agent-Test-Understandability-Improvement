package org.threeten.extra.chrono;

import java.time.DayOfWeek;
import java.time.Month;

import org.junit.jupiter.api.Test;

import com.google.common.testing.EqualsTester;

/**
 * Tests {@link AccountingChronology#equals(Object)} and {@link AccountingChronology#hashCode()}
 * by way of the dates it produces.
 */
public class TestAccountingChronology_test_equals_and_hashCode {

    /**
     * Reference chronology: accounting year ends on the Sunday nearest the end of August,
     * split into thirteen even 4-week months, with the leap week landing in month 13.
     */
    private static final AccountingChronology INSTANCE = new AccountingChronologyBuilder()
            .endsOn(DayOfWeek.SUNDAY)
            .nearestEndOf(Month.AUGUST)
            .withDivision(AccountingYearDivision.THIRTEEN_EVEN_MONTHS_OF_4_WEEKS)
            .leapWeekInMonth(13)
            .toChronology();

    @Test
    public void test_equals_and_hashCode() {
        // A distinct chronology: ends on Wednesday (not Sunday) and anchored to the ISO year
        // it ends in, so its dates must never be equal to INSTANCE's dates.
        AccountingChronology other = new AccountingChronologyBuilder()
                .endsOn(DayOfWeek.WEDNESDAY)
                .nearestEndOf(Month.AUGUST)
                .leapWeekInMonth(13)
                .withDivision(AccountingYearDivision.THIRTEEN_EVEN_MONTHS_OF_4_WEEKS)
                .accountingYearEndsInIsoYear()
                .toChronology();

        new EqualsTester()
                // Each group holds dates that must be equal to each other but unequal to every other group.
                .addEqualityGroup(INSTANCE.date(2000, 1, 3), INSTANCE.date(2000, 1, 3))
                .addEqualityGroup(INSTANCE.date(2000, 1, 4), INSTANCE.date(2000, 1, 4))
                .addEqualityGroup(INSTANCE.date(2000, 2, 3), INSTANCE.date(2000, 2, 3))
                .addEqualityGroup(INSTANCE.date(2001, 1, 3), INSTANCE.date(2001, 1, 3))
                .addEqualityGroup(other.date(2000, 1, 3), other.date(2000, 1, 3))
                .testEquals();
    }
}
