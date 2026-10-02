package org.threeten.extra;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;

/**
 * Verifies that {@link Hours#parse(CharSequence)} accepts every valid ISO-8601
 * "PnDTnH" style text and converts it to the expected total number of hours.
 */
public class TestHours_test_parse_CharSequence_valid {

    private static final int HOURS_PER_DAY = 24;

    /**
     * Provides valid input texts paired with the total number of hours they
     * should parse to. Cases are grouped by the kind of input being exercised.
     */
    public static Object[][] data_valid() {
        return new Object[][] {
                // Hours-only section ("PTnH"), no sign on the value.
                { "PT0H", 0 },
                { "PT1H", 1 },
                { "PT2H", 2 },
                { "PT123456789H", 123456789 },

                // Hours-only section with an explicit sign on the value.
                { "PT+0H", 0 },
                { "PT+2H", 2 },
                { "PT-0H", 0 },
                { "PT-2H", -2 },

                // Days-only section ("PnD"), no sign; each day is 24 hours.
                { "P0D", 0 * HOURS_PER_DAY },
                { "P1D", 1 * HOURS_PER_DAY },
                { "P2D", 2 * HOURS_PER_DAY },
                { "P1234567D", 1234567 * HOURS_PER_DAY },

                // Days-only section with an explicit sign on the value.
                { "P+0D", 0 * HOURS_PER_DAY },
                { "P+2D", 2 * HOURS_PER_DAY },
                { "P-0D", 0 * HOURS_PER_DAY },
                { "P-2D", -2 * HOURS_PER_DAY },

                // Combined days-and-hours sections ("PnDTnH").
                { "P0DT0H", 0 },
                { "P1DT2H", 1 * HOURS_PER_DAY + 2 },
        };
    }

    @ParameterizedTest
    @MethodSource("data_valid")
    public void test_parse_CharSequence_valid(String text, int expectedHours) {
        assertEquals(Hours.of(expectedHours), Hours.parse(text));
    }
}
