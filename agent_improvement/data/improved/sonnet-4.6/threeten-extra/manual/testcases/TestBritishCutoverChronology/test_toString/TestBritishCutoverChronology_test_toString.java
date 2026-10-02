package org.threeten.extra.chrono;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;

public class TestBritishCutoverChronology_test_toString {

    // Provides (date, expectedString) pairs covering an early AD date and a modern date.
    public static Object[][] data_toString() {
        return new Object[][] {
            { BritishCutoverDate.of(1, 1, 1),       "BritishCutover AD 1-01-01"    },
            { BritishCutoverDate.of(2012, 6, 23),   "BritishCutover AD 2012-06-23" },
        };
    }

    @ParameterizedTest
    @MethodSource("data_toString")
    public void test_toString(BritishCutoverDate date, String expected) {
        assertEquals(expected, date.toString());
    }
}
