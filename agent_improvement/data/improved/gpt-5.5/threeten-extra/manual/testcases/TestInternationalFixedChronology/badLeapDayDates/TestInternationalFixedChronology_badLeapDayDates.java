package org.threeten.extra.chrono;

import static org.junit.jupiter.api.Assertions.assertThrows;

import java.time.DateTimeException;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;

@SuppressWarnings({ "static-method" })
public class TestInternationalFixedChronology_badLeapDayDates {

    private static final int LEAP_DAY_MONTH = 6;
    private static final int LEAP_DAY = 29;

    public static Object[][] data_badLeapDates() {
        return new Object[][] {
                { 1 },
                { 100 },
                { 200 },
                { 300 },
                { 1900 }
        };
    }

    @ParameterizedTest
    @MethodSource("data_badLeapDates")
    public void badLeapDayDates(int year) {
        assertThrows(DateTimeException.class, () -> InternationalFixedDate.of(year, LEAP_DAY_MONTH, LEAP_DAY));
    }
}
