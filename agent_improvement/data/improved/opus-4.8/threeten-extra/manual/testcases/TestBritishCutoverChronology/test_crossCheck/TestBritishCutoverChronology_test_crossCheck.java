package org.threeten.extra.chrono;

import static java.time.temporal.ChronoField.DAY_OF_MONTH;
import static java.time.temporal.ChronoField.MONTH_OF_YEAR;
import static java.time.temporal.ChronoField.YEAR_OF_ERA;
import static java.time.temporal.ChronoUnit.DAYS;
import static org.junit.jupiter.api.Assertions.assertEquals;

import java.time.Instant;
import java.time.LocalDate;
import java.time.ZoneOffset;
import java.time.ZonedDateTime;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.TimeZone;

import org.junit.jupiter.api.Test;

public class TestBritishCutoverChronology_test_crossCheck {

    /**
     * Walks every day from 1700-01-01 up to (but not including) 1800-01-01 and checks that
     * {@link BritishCutoverDate} agrees, field by field, with a {@link GregorianCalendar} that has
     * been configured to switch from the Julian to the Gregorian calendar on the same British
     * cutover date (14 September 1752).
     */
    @Test
    public void test_crossCheck() {
        // Configure a GregorianCalendar whose Julian -> Gregorian switch matches the British cutover.
        Instant britishCutover = ZonedDateTime.of(1752, 9, 14, 0, 0, 0, 0, ZoneOffset.UTC).toInstant();
        GregorianCalendar reference = new GregorianCalendar(TimeZone.getTimeZone("UTC"));
        reference.setGregorianChange(Date.from(britishCutover));
        reference.clear();
        reference.set(1700, Calendar.JANUARY, 1);

        BritishCutoverDate date = BritishCutoverDate.of(1700, 1, 1);
        BritishCutoverDate end = BritishCutoverDate.of(1800, 1, 1);
        while (date.isBefore(end)) {
            // Calendar.MONTH is zero-based, so add 1 to compare against MONTH_OF_YEAR.
            assertEquals(reference.get(Calendar.YEAR), date.get(YEAR_OF_ERA));
            assertEquals(reference.get(Calendar.MONTH) + 1, date.get(MONTH_OF_YEAR));
            assertEquals(reference.get(Calendar.DAY_OF_MONTH), date.get(DAY_OF_MONTH));
            assertEquals(reference.toZonedDateTime().toLocalDate(), LocalDate.from(date));

            // Advance both representations by a single day and re-check.
            reference.add(Calendar.DAY_OF_MONTH, 1);
            date = date.plus(1, DAYS);
        }
    }
}
