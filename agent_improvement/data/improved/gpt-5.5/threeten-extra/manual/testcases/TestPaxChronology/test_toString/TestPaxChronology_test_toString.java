package org.threeten.extra.chrono;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;

@SuppressWarnings("static-method")
public class TestPaxChronology_test_toString {

    public static Object[][] data_toString() {
        return new Object[][] {
                {PaxDate.of(1, 1, 1), "Pax CE 1-01-01"},
                {PaxDate.of(2012, 6, 23), "Pax CE 2012-06-23"},
        };
    }

    @ParameterizedTest
    @MethodSource("data_toString")
    public void test_toString(PaxDate paxDate, String expectedText) {
        assertEquals(expectedText, paxDate.toString());
    }
}
