package org.threeten.extra;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.time.Clock;
import java.time.Instant;
import java.time.LocalDate;
import java.time.ZoneId;

import org.junit.jupiter.api.Test;

public class TestDayOfYear_test_now_clock_notLeapYear {

    private static final int STANDARD_YEAR_LENGTH = 365;
    private static final int STANDARD_YEAR = 2007;
    private static final ZoneId PARIS = ZoneId.of("Europe/Paris");

    @Test
    public void test_now_clock_notLeapYear() {
        LocalDate date = LocalDate.of(STANDARD_YEAR, 1, 1);

        for (int expectedDayOfYear = 1; expectedDayOfYear <= STANDARD_YEAR_LENGTH; expectedDayOfYear++) {
            Instant instant = date.atStartOfDay(PARIS).toInstant();
            Clock clock = Clock.fixed(instant, PARIS);

            DayOfYear actualDayOfYear = DayOfYear.now(clock);

            assertEquals(expectedDayOfYear, actualDayOfYear.getValue());
            date = date.plusDays(1);
        }
    }
}
