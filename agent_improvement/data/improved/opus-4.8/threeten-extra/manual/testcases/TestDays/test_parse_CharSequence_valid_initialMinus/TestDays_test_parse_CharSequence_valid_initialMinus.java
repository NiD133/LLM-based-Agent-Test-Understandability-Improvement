package org.threeten.extra;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;

/**
 * Tests that {@link Days#parse(CharSequence)} accepts an ISO-8601 period text
 * that carries a leading minus sign (e.g. {@code "-P2D"}).
 * <p>
 * Per the {@code parse} contract, a leading {@code "-"} negates the whole
 * amount. Each case therefore takes a well-formed period text {@code str} whose
 * day-value is {@code expectedDays}, prefixes it with {@code "-"}, and asserts
 * that parsing yields the negated amount {@code Days.of(-expectedDays)}.
 */
public class TestDays_test_parse_CharSequence_valid_initialMinus {

    /**
     * Valid period texts paired with the number of days each represents.
     * <p>
     * The collection exercises day-only ({@code D}) and week-only ({@code W})
     * sections as well as combined ({@code W} then {@code D}) sections, each
     * with optional explicit {@code +}/{@code -} signs on the numbers.
     */
    public static Object[][] data_valid() {
        return new Object[][] {
            // day-only sections
            { "P0D", 0 },
            { "P1D", 1 },
            { "P2D", 2 },
            { "P123456789D", 123456789 },
            { "P+0D", 0 },
            { "P+2D", 2 },
            { "P-0D", 0 },
            { "P-2D", -2 },
            // week-only sections (1 week == 7 days)
            { "P0W", 0 },
            { "P1W", 7 },
            { "P2W", 14 },
            { "P12345678W", 12345678 * 7 },
            { "P+0W", 0 },
            { "P+2W", 14 },
            { "P-0W", 0 },
            { "P-2W", -14 },
            // combined week and day sections
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
    public void test_parse_CharSequence_valid_initialMinus(String str, int expectedDays) {
        // A leading "-" negates the entire parsed amount.
        Days parsed = Days.parse("-" + str);

        assertEquals(Days.of(-expectedDays), parsed);
    }
}
