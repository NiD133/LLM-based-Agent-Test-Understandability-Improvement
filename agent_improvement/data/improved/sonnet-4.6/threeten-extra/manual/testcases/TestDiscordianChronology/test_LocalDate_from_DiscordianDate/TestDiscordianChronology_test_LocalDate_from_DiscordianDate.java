package org.threeten.extra.chrono;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.time.LocalDate;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;

/**
 * Tests that {@link LocalDate#from(java.time.temporal.TemporalAccessor)} correctly converts
 * a {@link DiscordianDate} to its ISO equivalent.
 *
 * <p>The Discordian calendar uses YOLD (Year of Our Lady of Discord) years, which are
 * offset from ISO years by 1166 (Discordian year 1167 == ISO year 1).
 * Leap years align with ISO leap years; St. Tib's Day (month=0, day=0) corresponds
 * to ISO February 29th.
 */
public class TestDiscordianChronology_test_LocalDate_from_DiscordianDate {

    /**
     * Provides pairs of (DiscordianDate, expected ISO LocalDate) covering:
     * <ul>
     *   <li>Ancient dates</li>
     *   <li>Sequential days in a non-leap year around the Feb 28/Mar 1 boundary</li>
     *   <li>St. Tib's Day in a leap year (month=0, day=0 → ISO Feb 29)</li>
     *   <li>A century year (ISO 100) that is not a leap year</li>
     *   <li>Year-end dates</li>
     *   <li>Historical and modern dates</li>
     * </ul>
     */
    public static Object[][] data_samples() {
        return new Object[][] {
            // Ancient dates: Discordian YOLD 2 and 166 correspond to very early ISO years
            { DiscordianDate.of(2,    1,  1), LocalDate.of(-1164,  1,  1) },
            { DiscordianDate.of(166,  1,  1), LocalDate.of(-1000,  1,  1) },
            { DiscordianDate.of(1156, 1,  1), LocalDate.of(  -10,  1,  1) },
            { DiscordianDate.of(1166, 1,  1), LocalDate.of(    0,  1,  1) },

            // Sequential days in YOLD 1167 (ISO year 1), a non-leap year
            { DiscordianDate.of(1167, 1,  1), LocalDate.of(1,  1,  1) },
            { DiscordianDate.of(1167, 1,  2), LocalDate.of(1,  1,  2) },
            { DiscordianDate.of(1167, 1,  3), LocalDate.of(1,  1,  3) },
            // Days 57-59 land in February (no leap day)
            { DiscordianDate.of(1167, 1, 57), LocalDate.of(1,  2, 26) },
            { DiscordianDate.of(1167, 1, 58), LocalDate.of(1,  2, 27) },
            { DiscordianDate.of(1167, 1, 59), LocalDate.of(1,  2, 28) },
            // Day 60 jumps directly to March 1 because there is no Feb 29 in a non-leap year
            { DiscordianDate.of(1167, 1, 60), LocalDate.of(1,  3,  1) },

            // YOLD 1170 (ISO year 4) is a leap year: days 57-59 still in February …
            { DiscordianDate.of(1170, 1, 57), LocalDate.of(4,  2, 26) },
            { DiscordianDate.of(1170, 1, 58), LocalDate.of(4,  2, 27) },
            { DiscordianDate.of(1170, 1, 59), LocalDate.of(4,  2, 28) },
            // … St. Tib's Day (month=0, day=0) is the intercalary leap day = ISO Feb 29
            { DiscordianDate.of(1170, 0,  0), LocalDate.of(4,  2, 29) },
            // Day 60 of month 1 follows St. Tib's Day, landing on ISO Mar 1
            { DiscordianDate.of(1170, 1, 60), LocalDate.of(4,  3,  1) },

            // YOLD 1266 (ISO year 100): a century year NOT divisible by 400, so not a leap year
            { DiscordianDate.of(1266, 1, 57), LocalDate.of(100, 2, 26) },
            { DiscordianDate.of(1266, 1, 58), LocalDate.of(100, 2, 27) },
            { DiscordianDate.of(1266, 1, 59), LocalDate.of(100, 2, 28) },
            // No St. Tib's Day; day 60 maps directly to Mar 1
            { DiscordianDate.of(1266, 1, 60), LocalDate.of(100, 3,  1) },
            { DiscordianDate.of(1266, 1, 61), LocalDate.of(100, 3,  2) },

            // Year-end dates in YOLD 1166 (ISO year 0)
            { DiscordianDate.of(1166, 5, 72), LocalDate.of(0, 12, 30) },
            { DiscordianDate.of(1166, 5, 73), LocalDate.of(0, 12, 31) },

            // Historically notable dates
            { DiscordianDate.of(2748, 4, 68), LocalDate.of(1582, 10, 14) }, // day before Gregorian reform
            { DiscordianDate.of(2748, 4, 69), LocalDate.of(1582, 10, 15) }, // first day of Gregorian calendar
            { DiscordianDate.of(3111, 5, 24), LocalDate.of(1945, 11, 12) }, // end of World War II era

            // Modern dates
            { DiscordianDate.of(3178, 3, 40), LocalDate.of(2012,  7,  5) },
            { DiscordianDate.of(3178, 3, 41), LocalDate.of(2012,  7,  6) },
        };
    }

    @ParameterizedTest
    @MethodSource("data_samples")
    public void test_LocalDate_from_DiscordianDate(DiscordianDate discordian, LocalDate iso) {
        assertEquals(iso, LocalDate.from(discordian));
    }
}
