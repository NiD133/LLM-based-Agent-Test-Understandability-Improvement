package org.threeten.extra;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;

/**
 * Tests that {@link Days#parse(CharSequence)} accepts an ISO-8601 period string
 * that carries an explicit leading plus sign (for example {@code "+P2D"}).
 * <p>
 * Each case reuses a base period string without the sign; the test prepends
 * {@code "+"} before parsing. Because a leading plus is a no-op, the parsed
 * result must equal {@code Days.of(expectedDays)}.
 */
public class TestDays_test_parse_CharSequence_valid_initialPlus {

    /**
     * Supplies pairs of {base period string, expected day count}.
     * The expected count already accounts for week-to-day conversion (1 week = 7 days).
     */
    public static Object[][] data_valid() {
        return new Object[][] {
            // days only
            { "P0D", 0 },
            { "P1D", 1 },
            { "P2D", 2 },
            { "P123456789D", 123456789 },
            { "P+0D", 0 },
            { "P+2D", 2 },
            { "P-0D", 0 },
            { "P-2D", -2 },
            // weeks only (1 week = 7 days)
            { "P0W", 0 },
            { "P1W", 7 },
            { "P2W", 14 },
            { "P12345678W", 12345678 * 7 },
            { "P+0W", 0 },
            { "P+2W", 14 },
            { "P-0W", 0 },
            { "P-2W", -14 },
            // weeks combined with days
            { "P0W0D", 0 },
            { "P2W3D", 17 },
            { "P+2W3D", 17 },
            { "P2W+3D", 17 },
            { "P-2W3D", -11 },
            { "P2W-3D", 11 },
            { "P-2W-3D", -17 },
        };
    }

    @ParameterizedTest
    @MethodSource("data_valid")
    public void test_parse_CharSequence_valid_initialPlus(String basePeriod, int expectedDays) {
        Days parsed = Days.parse("+" + basePeriod);

        assertEquals(Days.of(expectedDays), parsed);
    }
}
