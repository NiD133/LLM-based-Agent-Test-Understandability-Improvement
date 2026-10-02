package org.threeten.extra.chrono;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.stream.Stream;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

@SuppressWarnings({"static-method"})
public class TestSymmetry454Chronology_test_lengthOfMonth {

    private static final int NORMAL_MONTH_LENGTH = 28;
    private static final int LONG_MONTH_LENGTH = 35;

    public static Stream<Arguments> data_lengthOfMonth() {
        return Stream.of(
                Arguments.of(2000, 1, 28, NORMAL_MONTH_LENGTH),
                Arguments.of(2000, 2, 28, LONG_MONTH_LENGTH),
                Arguments.of(2000, 3, 28, NORMAL_MONTH_LENGTH),
                Arguments.of(2000, 4, 28, NORMAL_MONTH_LENGTH),
                Arguments.of(2000, 5, 28, LONG_MONTH_LENGTH),
                Arguments.of(2000, 6, 28, NORMAL_MONTH_LENGTH),
                Arguments.of(2000, 7, 28, NORMAL_MONTH_LENGTH),
                Arguments.of(2000, 8, 28, LONG_MONTH_LENGTH),
                Arguments.of(2000, 9, 28, NORMAL_MONTH_LENGTH),
                Arguments.of(2000, 10, 28, NORMAL_MONTH_LENGTH),
                Arguments.of(2000, 11, 28, LONG_MONTH_LENGTH),
                Arguments.of(2000, 12, 28, NORMAL_MONTH_LENGTH),
                Arguments.of(2004, 12, 20, LONG_MONTH_LENGTH));
    }

    @ParameterizedTest
    @MethodSource("data_lengthOfMonth")
    public void test_lengthOfMonth(int year, int month, int day, int expectedLength) {
        assertEquals(expectedLength, Symmetry454Date.of(year, month, day).lengthOfMonth());
    }
}
