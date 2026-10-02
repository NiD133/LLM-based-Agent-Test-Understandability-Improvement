package org.threeten.extra;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;

public class TestMonths_test_parse_CharSequence_valid {

    /**
     * Valid ISO-8601 period strings and their expected month counts.
     *
     * Groups:
     *   - Pure months (PnM): integer, with explicit plus/minus prefix
     *   - Pure years  (PnY): converted to months by multiplying by 12
     *   - Combined    (PnYnM): years and months summed, signs applied independently
     */
    public static Object[][] data_valid() {
        return new Object[][] {
            // --- pure months: PnM ---
            { "P0M",         0 },
            { "P1M",         1 },
            { "P2M",         2 },
            { "P123456789M", 123456789 },
            { "P+0M",        0 },
            { "P+2M",        2 },
            { "P-0M",        0 },
            { "P-2M",       -2 },

            // --- pure years: PnY (1 year = 12 months) ---
            { "P0Y",         0 },
            { "P1Y",        12 },
            { "P2Y",        24 },
            { "P1234567Y",   1234567 * 12 },
            { "P+0Y",        0 },
            { "P+2Y",       24 },
            { "P-0Y",        0 },
            { "P-2Y",      -24 },

            // --- combined years and months: PnYnM ---
            { "P0Y0M",       0  },
            { "P2Y3M",      27  },   // +24 + 3
            { "P+2Y3M",     27  },   // +24 + 3  (explicit plus on years)
            { "P2Y+3M",     27  },   // +24 + 3  (explicit plus on months)
            { "P-2Y3M",    -21  },   // -24 + 3
            { "P2Y-3M",     21  },   // +24 - 3
            { "P-2Y-3M",   -27  },   // -24 - 3
        };
    }

    public static Object[][] data_invalid() {
        return new Object[][] {
            { "P3W" }, { "P3D" }, { "P3Q" }, { "P1M2Y" },
            { "3" }, { "-3" }, { "3M" }, { "-3M" },
            { "P3" }, { "P-3" }, { "PM" },
        };
    }

    @ParameterizedTest
    @MethodSource("data_valid")
    public void test_parse_CharSequence_valid(String str, int expectedMonths) {
        assertEquals(Months.of(expectedMonths), Months.parse(str));
    }
}
