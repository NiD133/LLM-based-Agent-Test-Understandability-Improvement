package org.threeten.extra;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.time.LocalDate;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;

import org.junit.jupiter.api.Test;
import org.junitpioneer.jupiter.RetryingTest;

/**
 * Tests the factory methods that obtain a {@link DayOfMonth} from the current
 * date or by parsing a {@link CharSequence}.
 */
public class TestDayOfMonth_test_from_parse_CharSequence {

    /**
     * {@code DayOfMonth.now()} should report the same day-of-month as the
     * current local date in the default time-zone.
     */
    @RetryingTest(100)
    public void now_matchesCurrentLocalDate() {
        int expectedDay = LocalDate.now().getDayOfMonth();
        assertEquals(expectedDay, DayOfMonth.now().getValue());
    }

    /**
     * {@code DayOfMonth.now(zone)} should report the same day-of-month as the
     * current local date in the given time-zone.
     */
    @RetryingTest(100)
    public void now_withZone_matchesCurrentLocalDateInThatZone() {
        ZoneId tokyo = ZoneId.of("Asia/Tokyo");
        int expectedDay = LocalDate.now(tokyo).getDayOfMonth();
        assertEquals(expectedDay, DayOfMonth.now(tokyo).getValue());
    }

    /**
     * Parsing the text "3" with a day-of-month formatter and the
     * {@code DayOfMonth::from} query should yield {@code DayOfMonth.of(3)}.
     */
    @Test
    public void from_usedAsParseQuery_buildsDayOfMonth() {
        DateTimeFormatter dayOfMonthFormatter = DateTimeFormatter.ofPattern("d");
        DayOfMonth parsed = dayOfMonthFormatter.parse("3", DayOfMonth::from);
        assertEquals(DayOfMonth.of(3), parsed);
    }
}
