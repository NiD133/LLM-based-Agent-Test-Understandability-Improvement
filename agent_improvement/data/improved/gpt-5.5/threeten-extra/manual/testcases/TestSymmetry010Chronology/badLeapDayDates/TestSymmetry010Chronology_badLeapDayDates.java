package org.threeten.extra.chrono;

import static org.junit.jupiter.api.Assertions.assertThrows;

import java.time.DateTimeException;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;

@SuppressWarnings({ "static-method" })
public class TestSymmetry010Chronology_badLeapDayDates {

    private static final int DECEMBER = 12;
    private static final int LEAP_WEEK_LAST_DAY = 37;

    public static Object[][] nonLeapYears() {
        return new Object[][] {
                { 1 },
                { 100 },
                { 200 },
                { 2000 }
        };
    }

    @ParameterizedTest
    @MethodSource("nonLeapYears")
    public void rejectsLeapWeekDateInNonLeapYears(int year) {
        assertThrows(DateTimeException.class, () -> Symmetry010Date.of(year, DECEMBER, LEAP_WEEK_LAST_DAY));
    }
}
