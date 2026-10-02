package org.threeten.extra.chrono;

import static org.junit.jupiter.api.Assertions.assertThrows;

import java.time.DateTimeException;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;

@SuppressWarnings("static-method")
public class TestSymmetry010Chronology_test_badDates {

    public static Object[][] data_badDates() {
        return new Object[][] {
                invalidDate(-1, 13, 28),
                invalidDate(-1, 13, 29),

                invalidDate(2000, -2, 1),
                invalidDate(2000, 13, 1),
                invalidDate(2000, 15, 1),

                invalidDate(2000, 1, -1),
                invalidDate(2000, 1, 0),
                invalidDate(2000, 0, 1),
                invalidDate(2000, -1, 0),
                invalidDate(2000, -1, 1),

                invalidDate(2000, 1, 31),
                invalidDate(2000, 2, 32),
                invalidDate(2000, 3, 31),
                invalidDate(2000, 4, 31),
                invalidDate(2000, 5, 32),
                invalidDate(2000, 6, 31),
                invalidDate(2000, 7, 31),
                invalidDate(2000, 8, 32),
                invalidDate(2000, 9, 31),
                invalidDate(2000, 10, 31),
                invalidDate(2000, 11, 32),
                invalidDate(2000, 12, 31),

                invalidDate(2004, 12, 38),
        };
    }

    private static Object[] invalidDate(int year, int month, int dayOfMonth) {
        return new Object[] { year, month, dayOfMonth };
    }

    @ParameterizedTest
    @MethodSource("data_badDates")
    public void test_badDates(int year, int month, int dom) {
        assertThrows(DateTimeException.class, () -> Symmetry010Date.of(year, month, dom));
    }
}
