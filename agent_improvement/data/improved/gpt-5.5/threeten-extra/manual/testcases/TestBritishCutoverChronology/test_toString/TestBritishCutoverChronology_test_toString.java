package org.threeten.extra.chrono;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;

public class TestBritishCutoverChronology_test_toString {

    public static Object[][] data_toString() {
        return new Object[][] {
                toStringCase(BritishCutoverDate.of(1, 1, 1), "BritishCutover AD 1-01-01"),
                toStringCase(BritishCutoverDate.of(2012, 6, 23), "BritishCutover AD 2012-06-23")
        };
    }

    private static Object[] toStringCase(BritishCutoverDate date, String expectedText) {
        return new Object[] { date, expectedText };
    }

    @ParameterizedTest
    @MethodSource("data_toString")
    public void test_toString(BritishCutoverDate cutover, String expected) {
        assertEquals(expected, cutover.toString());
    }
}
