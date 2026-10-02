package org.threeten.extra.chrono;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.time.temporal.TemporalAdjusters;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;

public class TestBritishCutoverChronology_test_adjust_lastDayOfMonth {

    /**
     * Each case pairs a starting date with the date expected after adjusting it
     * to the last day of its month. The 1752 cases cover the British cutover
     * month, where the calendar skips from September 2 to September 14.
     */
    public static Object[][] data_lastDayOfMonth() {
        return new Object[][] {
            { BritishCutoverDate.of(1752, 2, 23), BritishCutoverDate.of(1752, 2, 29) },
            { BritishCutoverDate.of(1752, 6, 23), BritishCutoverDate.of(1752, 6, 30) },
            { BritishCutoverDate.of(1752, 9, 2), BritishCutoverDate.of(1752, 9, 30) },
            { BritishCutoverDate.of(1752, 9, 14), BritishCutoverDate.of(1752, 9, 30) },
            { BritishCutoverDate.of(2012, 2, 23), BritishCutoverDate.of(2012, 2, 29) },
            { BritishCutoverDate.of(2012, 6, 23), BritishCutoverDate.of(2012, 6, 30) },
        };
    }

    @ParameterizedTest
    @MethodSource("data_lastDayOfMonth")
    public void test_adjust_lastDayOfMonth(BritishCutoverDate input, BritishCutoverDate expected) {
        BritishCutoverDate adjusted = input.with(TemporalAdjusters.lastDayOfMonth());
        assertEquals(expected, adjusted);
    }
}
