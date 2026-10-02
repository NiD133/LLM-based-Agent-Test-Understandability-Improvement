package org.threeten.extra.chrono;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.stream.Stream;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

@SuppressWarnings("static-method")
public class TestPaxChronology_test_lengthOfMonth {

    private static Stream<Arguments> data_lengthOfMonth() {
        return Stream.of(
                Arguments.of(1900, 1, 28),
                Arguments.of(1900, 2, 28),
                Arguments.of(1900, 3, 28),
                Arguments.of(1900, 4, 28),
                Arguments.of(1900, 5, 28),
                Arguments.of(1900, 6, 28),
                Arguments.of(1900, 7, 28),
                Arguments.of(1900, 8, 28),
                Arguments.of(1900, 9, 28),
                Arguments.of(1900, 10, 28),
                Arguments.of(1900, 11, 28),
                Arguments.of(1900, 12, 28),
                Arguments.of(1900, 13, 7),
                Arguments.of(1900, 14, 28),
                Arguments.of(1901, 13, 28),
                Arguments.of(1902, 13, 28),
                Arguments.of(1903, 13, 28),
                Arguments.of(1904, 13, 28),
                Arguments.of(1905, 13, 28),
                Arguments.of(1906, 13, 7),
                Arguments.of(2000, 13, 28),
                Arguments.of(2100, 13, 7));
    }

    @ParameterizedTest
    @MethodSource("data_lengthOfMonth")
    public void test_lengthOfMonth(int year, int month, int length) {
        assertEquals(length, PaxDate.of(year, month, 1).lengthOfMonth());
    }
}
