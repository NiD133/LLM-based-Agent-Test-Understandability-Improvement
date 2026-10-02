package org.threeten.extra.chrono;

import static org.junit.jupiter.api.Assertions.assertNull;

import java.time.DayOfWeek;
import java.time.Month;

import org.junit.jupiter.api.Test;

/**
 * Verifies that {@link AccountingChronology#getCalendarType()} returns {@code null}.
 *
 * The Unicode LDML specification does not define an identifier for 52/53-week accounting
 * calendars, so the calendar type is intentionally {@code null} rather than a recognised
 * CLDR key.
 */
public class TestAccountingChronology_test_chronology_of_name_id {

    /**
     * A representative AccountingChronology instance: ends on Sunday nearest the end of
     * August, divided into thirteen 4-week months, with the leap week in month 13.
     */
    private static final AccountingChronology INSTANCE =
            new AccountingChronologyBuilder()
                    .endsOn(DayOfWeek.SUNDAY)
                    .nearestEndOf(Month.AUGUST)
                    .withDivision(AccountingYearDivision.THIRTEEN_EVEN_MONTHS_OF_4_WEEKS)
                    .leapWeekInMonth(13)
                    .toChronology();

    @Test
    public void test_chronology_of_name_id() {
        // The AccountingChronology has no LDML calendar type, so getCalendarType() must be null.
        assertNull(INSTANCE.getCalendarType());
    }
}
