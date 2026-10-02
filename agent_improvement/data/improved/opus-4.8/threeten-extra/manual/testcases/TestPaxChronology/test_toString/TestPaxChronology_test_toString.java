package org.threeten.extra.chrono;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;

/**
 * Tests that {@link PaxDate#toString()} renders a date in the
 * {@code "Pax <era> <year>-<month>-<day>"} format, with the year shown as-is
 * and the month and day zero-padded to two digits.
 */
@SuppressWarnings({ "static-method" })
public class TestPaxChronology_test_toString {

    /**
     * Each case pairs a {@link PaxDate} with its expected {@code toString()} text.
     */
    public static Object[][] data_toString() {
        return new Object[][] {
            { PaxDate.of(1, 1, 1), "Pax CE 1-01-01" },
            { PaxDate.of(2012, 6, 23), "Pax CE 2012-06-23" },
        };
    }

    @ParameterizedTest
    @MethodSource("data_toString")
    public void test_toString(PaxDate pax, String expected) {
        assertEquals(expected, pax.toString());
    }
}
