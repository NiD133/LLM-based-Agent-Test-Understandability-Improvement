package org.threeten.extra;

import static org.junit.jupiter.api.Assertions.assertThrows;

import java.time.format.DateTimeParseException;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;

public class TestMinutes_test_parse_CharSequence_invalid {

    /**
     * Returns strings that are structurally invalid for {@link Minutes#parse(CharSequence)}.
     * The inputs are grouped by the reason they are rejected:
     * <ul>
     *   <li>Unsupported ISO-8601 designators (weeks 'W', unknown 'Q', months 'M' in date position, seconds 'S')</li>
     *   <li>Missing the required leading 'P' designator</li>
     *   <li>Incomplete patterns that have no parseable time component</li>
     * </ul>
     */
    public static Object[][] data_invalid() {
        return new Object[][] {
            // Unsupported designators: weeks, unknown unit, months-before-years, seconds
            { "P3W"   },
            { "P3Q"   },
            { "P1M2Y" },
            { "PT3S"  },

            // Missing the leading 'P' prefix
            { "3"   },
            { "-3"  },
            { "3M"  },
            { "-3M" },
            { "T3"  },

            // Incomplete patterns — no parseable time component
            { "P3M"  },
            { "P3"   },
            { "P-3"  },
            { "PM"   },
            { "P3M"  },
            { "PT3"  },
        };
    }

    @ParameterizedTest
    @MethodSource("data_invalid")
    @DisplayName("Minutes.parse throws DateTimeParseException for invalid input")
    public void test_parse_CharSequence_invalid(String str) {
        assertThrows(DateTimeParseException.class, () -> Minutes.parse(str));
    }
}
