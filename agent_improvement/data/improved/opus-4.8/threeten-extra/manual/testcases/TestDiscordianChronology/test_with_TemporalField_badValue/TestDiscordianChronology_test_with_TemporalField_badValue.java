package org.threeten.extra.chrono;

import static java.time.temporal.ChronoField.DAY_OF_MONTH;
import static java.time.temporal.ChronoField.DAY_OF_WEEK;
import static java.time.temporal.ChronoField.DAY_OF_YEAR;
import static java.time.temporal.ChronoField.MONTH_OF_YEAR;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.time.DateTimeException;
import java.time.temporal.TemporalField;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;

/**
 * Tests that {@link DiscordianDate#with(TemporalField, long)} rejects values that fall
 * outside the valid range of the requested field.
 */
public class TestDiscordianChronology_test_with_TemporalField_badValue {

    /**
     * Each case is a valid Discordian date together with a field and a value that is
     * out of range for that field, so adjusting the date must fail.
     *
     * Arguments: (year, month, dayOfMonth, field, outOfRangeValue)
     */
    public static Object[][] data_with_bad() {
        return new Object[][] {
            // DAY_OF_WEEK valid range is 1..5
            { 2013, 1, 1, DAY_OF_WEEK, 0 },
            { 2013, 1, 1, DAY_OF_WEEK, 6 },
            { 2014, 1, 1, DAY_OF_WEEK, -1 },
            { 2014, 1, 1, DAY_OF_WEEK, 6 },
            // DAY_OF_MONTH valid range is 1..73
            { 2013, 1, 1, DAY_OF_MONTH, 0 },
            { 2013, 1, 1, DAY_OF_MONTH, 74 },
            { 2014, 1, 1, DAY_OF_MONTH, -1 },
            { 2014, 1, 1, DAY_OF_MONTH, 74 },
            // DAY_OF_YEAR valid range is 1..365 (366 in a leap year)
            { 2013, 1, 1, DAY_OF_YEAR, 0 },
            { 2014, 1, 1, DAY_OF_YEAR, 0 },
            { 2013, 1, 1, DAY_OF_YEAR, 367 },
            { 2014, 1, 1, DAY_OF_YEAR, 367 },
            // MONTH_OF_YEAR valid range is 1..5
            { 2013, 1, 1, MONTH_OF_YEAR, 0 },
            { 2013, 1, 1, MONTH_OF_YEAR, 6 },
            { 2014, 1, 1, MONTH_OF_YEAR, -1 },
            { 2014, 1, 1, MONTH_OF_YEAR, 6 },
        };
    }

    @ParameterizedTest
    @MethodSource("data_with_bad")
    public void test_with_TemporalField_badValue(int year, int month, int dom, TemporalField field, long value) {
        DiscordianDate date = DiscordianDate.of(year, month, dom);

        assertThrows(DateTimeException.class, () -> date.with(field, value));
    }
}
