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

    private static final int START_YEAR = 1700;
    private static final int END_YEAR = 1800;

    @Test
    public void test_crossCheck() {
        BritishCutoverDate test = BritishCutoverDate.of(START_YEAR, 1, 1);
        BritishCutoverDate end = BritishCutoverDate.of(END_YEAR, 1, 1);
        GregorianCalendar gcal = gregorianCalendarStartingAtBritishCutoverStart();

        while (test.isBefore(end)) {
            assertBritishDateMatchesGregorianCalendar(test, gcal);
            gcal.add(Calendar.DAY_OF_MONTH, 1);
            test = test.plus(1, DAYS);
        }
    }

    private static GregorianCalendar gregorianCalendarStartingAtBritishCutoverStart() {
        Instant cutover = ZonedDateTime.of(1752, 9, 14, 0, 0, 0, 0, ZoneOffset.UTC).toInstant();
        GregorianCalendar gcal = new GregorianCalendar(TimeZone.getTimeZone("UTC"));
        gcal.setGregorianChange(Date.from(cutover));
        gcal.clear();
        gcal.set(START_YEAR, Calendar.JANUARY, 1);
        return gcal;
    }

    private static void assertBritishDateMatchesGregorianCalendar(BritishCutoverDate test, GregorianCalendar gcal) {
        assertEquals(gcal.get(Calendar.YEAR), test.get(YEAR_OF_ERA));
        assertEquals(gcal.get(Calendar.MONTH) + 1, test.get(MONTH_OF_YEAR));
        assertEquals(gcal.get(Calendar.DAY_OF_MONTH), test.get(DAY_OF_MONTH));
        assertEquals(gcal.toZonedDateTime().toLocalDate(), LocalDate.from(test));
    }
}
