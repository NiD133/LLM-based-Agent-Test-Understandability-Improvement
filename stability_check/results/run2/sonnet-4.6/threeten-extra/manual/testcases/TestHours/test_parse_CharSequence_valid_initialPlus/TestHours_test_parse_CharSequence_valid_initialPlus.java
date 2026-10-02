package org.threeten.extra;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;

/**
 * Tests that {@link Hours#parse(CharSequence)} correctly handles a leading '+' sign
 * prepended to otherwise valid ISO-8601 period strings.
 */
public class TestHours_test_parse_CharSequence_valid_initialPlus {

    public static Object[][] data_valid() {
        return new Object[][] {
            // Hour-only strings (PTnH)
            { "PT0H",         0           },
            { "PT1H",         1           },
            { "PT2H",         2           },
            { "PT123456789H", 123456789   },
            { "PT+0H",        0           },
            { "PT+2H",        2           },
            { "PT-0H",        0           },
            { "PT-2H",        -2          },
            // Day-only strings (PnD) — converted to hours (1 day = 24 hours)
            { "P0D",          0 * 24      },
            { "P1D",          1 * 24      },
            { "P2D",          2 * 24      },
            { "P1234567D",    1234567 * 24},
            { "P+0D",         0 * 24      },
            { "P+2D",         2 * 24      },
            { "P-0D",         0 * 24      },
            { "P-2D",         -2 * 24     },
            // Combined day + hour strings (PnDTnH)
            { "P0DT0H",       0           },
            { "P1DT2H",       1 * 24 + 2  },
        };
    }

    /**
     * Verifies that prepending '+' to a valid period string produces the same
     * {@link Hours} value as the string without the prefix.
     */
    @ParameterizedTest
    @MethodSource("data_valid")
    public void test_parse_CharSequence_valid_initialPlus(String str, int expectedHours) {
        assertEquals(Hours.of(expectedHours), Hours.parse("+" + str));
    }
}
