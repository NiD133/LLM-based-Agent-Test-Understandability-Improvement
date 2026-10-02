import static java.time.temporal.ChronoField.ALIGNED_DAY_OF_WEEK_IN_MONTH;
import static java.time.temporal.ChronoField.ALIGNED_DAY_OF_WEEK_IN_YEAR;
import static java.time.temporal.ChronoField.ALIGNED_WEEK_OF_MONTH;
import static java.time.temporal.ChronoField.ALIGNED_WEEK_OF_YEAR;
import static java.time.temporal.ChronoField.DAY_OF_MONTH;
import static java.time.temporal.ChronoField.DAY_OF_WEEK;
import static java.time.temporal.ChronoField.DAY_OF_YEAR;
import static java.time.temporal.ChronoField.ERA;
import static java.time.temporal.ChronoField.MONTH_OF_YEAR;
import static java.time.temporal.ChronoField.PROLEPTIC_MONTH;
import static java.time.temporal.ChronoField.YEAR;
import static org.junit.jupiter.api.Assertions.assertEquals;

import java.time.temporal.TemporalField;

import org.threeten.extra.chrono.DiscordianDate;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;

/**
 * Verifies {@link DiscordianDate#getLong(TemporalField)} for a range of dates and fields.
 * <p>
 * The Discordian calendar has 5 months of 73 days, 5-day weeks, and a single era (YOLD).
 * St. Tib's Day (encoded as month 0, day 0) is a leap-day that belongs to no month or week,
 * so most position fields report 0 for it.
 */
public class TestDiscordianChronology_test_getLong {

    /**
     * Each case is {@code {year, month, dayOfMonth, field, expectedValue}}: the value
     * {@code getLong(field)} should return for the Discordian date {@code (year, month, dayOfMonth)}.
     */
    public static Object[][] data_getLong() {
        return new Object[][] {
            // A regular date early in the first month (year 2014, month 1, day 26).
            { 2014, 1, 26, DAY_OF_WEEK, 1 },
            { 2014, 1, 26, DAY_OF_MONTH, 26 },
            { 2014, 1, 26, DAY_OF_YEAR, 26 },
            { 2014, 1, 26, ALIGNED_DAY_OF_WEEK_IN_MONTH, 1 },
            { 2014, 1, 26, ALIGNED_WEEK_OF_MONTH, 6 },
            { 2014, 1, 26, ALIGNED_DAY_OF_WEEK_IN_YEAR, 1 },
            { 2014, 1, 26, ALIGNED_WEEK_OF_YEAR, 6 },
            { 2014, 1, 26, MONTH_OF_YEAR, 1 },
            { 2014, 1, 26, PROLEPTIC_MONTH, 2014 * 5 + 1 - 1 },
            { 2014, 1, 26, YEAR, 2014 },
            { 2014, 1, 26, ERA, 1 },

            // A regular date in the last month (year 2014, month 5, day 26).
            { 2014, 5, 26, DAY_OF_WEEK, 3 },
            { 2014, 5, 26, DAY_OF_MONTH, 26 },
            { 2014, 5, 26, DAY_OF_YEAR, 1 + 73 + 73 + 73 + 73 + 26 },
            { 2014, 5, 26, ALIGNED_DAY_OF_WEEK_IN_MONTH, 1 },
            { 2014, 5, 26, ALIGNED_WEEK_OF_MONTH, 6 },
            { 2014, 5, 26, ALIGNED_DAY_OF_WEEK_IN_YEAR, 3 },
            { 2014, 5, 26, ALIGNED_WEEK_OF_YEAR, 64 },
            { 2014, 5, 26, MONTH_OF_YEAR, 5 },
            { 2014, 5, 26, PROLEPTIC_MONTH, 2014 * 5 + 5 - 1 },
            { 2014, 5, 26, YEAR, 2014 },
            { 2014, 5, 26, ERA, 1 },

            // The single era is always 1, even for the very first supported year.
            { 1, 5, 8, ERA, 1 },

            // St. Tib's Day (month 0, day 0): a leap-day outside any month or week.
            { 2014, 0, 0, DAY_OF_WEEK, 0 },
            { 2014, 0, 0, DAY_OF_MONTH, 0 },
            { 2014, 0, 0, DAY_OF_YEAR, 60 },
            { 2014, 0, 0, ALIGNED_DAY_OF_WEEK_IN_MONTH, 0 },
            { 2014, 0, 0, ALIGNED_WEEK_OF_MONTH, 0 },
            { 2014, 0, 0, ALIGNED_DAY_OF_WEEK_IN_YEAR, 0 },
            { 2014, 0, 0, ALIGNED_WEEK_OF_YEAR, 0 },
            { 2014, 0, 0, MONTH_OF_YEAR, 0 },
            { 2014, 0, 0, PROLEPTIC_MONTH, 2014 * 5 + 1 - 1 },
        };
    }

    @ParameterizedTest
    @MethodSource("data_getLong")
    public void test_getLong(int year, int month, int dom, TemporalField field, long expected) {
        assertEquals(expected, DiscordianDate.of(year, month, dom).getLong(field));
    }
}
