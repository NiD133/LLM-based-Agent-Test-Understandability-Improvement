package org.threeten.extra.chrono;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;

/**
 * Tests that {@link JulianDate#toString()} renders a date as
 * "&lt;chronology&gt; &lt;era&gt; &lt;year&gt;-&lt;month&gt;-&lt;day&gt;",
 * with the month and day zero-padded to two digits.
 */
public class TestJulianChronology_test_toString {

    /**
     * Each case pairs a Julian date with its expected textual form.
     */
    public static Object[][] data_toString() {
        return new Object[][] {
            { JulianDate.of(1, 1, 1), "Julian AD 1-01-01" },
            { JulianDate.of(2012, 6, 23), "Julian AD 2012-06-23" },
        };
    }

    @ParameterizedTest
    @MethodSource("data_toString")
    public void test_toString(JulianDate julian, String expected) {
        assertEquals(expected, julian.toString());
    }
}
