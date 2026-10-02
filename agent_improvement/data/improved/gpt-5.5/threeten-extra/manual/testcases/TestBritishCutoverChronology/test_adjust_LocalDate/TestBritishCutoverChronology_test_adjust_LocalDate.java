package org.threeten.extra.chrono;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.time.LocalDate;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;

public class TestBritishCutoverChronology_test_adjust_LocalDate {

    public static Object[][] data_withLocalDate() {
        return new Object[][] {
                {
                        BritishCutoverDate.of(1752, 9, 2),
                        LocalDate.of(1752, 9, 12),
                        BritishCutoverDate.of(1752, 9, 1)
                },
                {
                        BritishCutoverDate.of(1752, 9, 14),
                        LocalDate.of(1752, 9, 12),
                        BritishCutoverDate.of(1752, 9, 1)
                },
                {
                        BritishCutoverDate.of(1752, 9, 2),
                        LocalDate.of(1752, 9, 14),
                        BritishCutoverDate.of(1752, 9, 14)
                },
                {
                        BritishCutoverDate.of(1752, 9, 15),
                        LocalDate.of(1752, 9, 14),
                        BritishCutoverDate.of(1752, 9, 14)
                },
                {
                        BritishCutoverDate.of(2012, 2, 23),
                        LocalDate.of(2012, 2, 23),
                        BritishCutoverDate.of(2012, 2, 23)
                }
        };
    }

    @ParameterizedTest
    @MethodSource("data_withLocalDate")
    public void test_adjust_LocalDate(BritishCutoverDate input, LocalDate local, BritishCutoverDate expected) {
        BritishCutoverDate test = input.with(local);
        assertEquals(expected, test);
    }
}
