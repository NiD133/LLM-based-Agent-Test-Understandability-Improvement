package org.threeten.extra.chrono;

import static org.junit.jupiter.api.Assertions.assertThrows;

import java.time.DateTimeException;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;

@SuppressWarnings({ "static-method" })
public class TestSymmetry454Chronology_test_badLeapDayDates {

    public static Object[][] data_badLeapDates() {
        return new Object[][] {
                { 1 },
                { 100 },
                { 200 },
                { 2000 },
        };
    }

    @ParameterizedTest
    @MethodSource("data_badLeapDates")
    public void test_badLeapDayDates(int year) {
        assertThrows(DateTimeException.class, () -> Symmetry454Date.of(year, 12, 29));
    }
}
