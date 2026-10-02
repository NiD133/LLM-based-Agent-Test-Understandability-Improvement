package org.threeten.extra.chrono;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;

/**
 * Tests {@link BritishCutoverDate#toString()}.
 * <p>
 * The string form is expected to be {@code "BritishCutover <era> <year>-<month>-<day>"},
 * with the month and day zero-padded to two digits.
 */
public class TestBritishCutoverChronology_test_toString {

    /**
     * Sample dates paired with their expected {@code toString()} representation.
     */
    public static Object[][] data_toString() {
        return new Object[][] {
            { BritishCutoverDate.of(1, 1, 1), "BritishCutover AD 1-01-01" },
            { BritishCutoverDate.of(2012, 6, 23), "BritishCutover AD 2012-06-23" },
        };
    }

    @ParameterizedTest
    @MethodSource("data_toString")
    public void test_toString(BritishCutoverDate cutoverDate, String expectedText) {
        assertEquals(expectedText, cutoverDate.toString());
    }
}
