package org.threeten.extra.chrono;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.stream.Stream;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

public class TestDiscordianChronology_test_lengthOfMonth {

    private static final int STANDARD_DISCORDIAN_MONTH_LENGTH = 73;

    static Stream<Arguments> data_lengthOfMonth() {
        return Stream.of(
                lengthCase(1900, 1),
                lengthCase(1900, 2),
                lengthCase(1900, 3),
                lengthCase(1900, 4),
                lengthCase(1900, 5),
                lengthCase(1901, 1),
                lengthCase(1902, 1),
                lengthCase(1903, 1),
                lengthCase(1904, 1),
                lengthCase(1966, 1),
                lengthCase(2066, 1));
    }

    private static Arguments lengthCase(int year, int month) {
        return Arguments.of(year, month, STANDARD_DISCORDIAN_MONTH_LENGTH);
    }

    @ParameterizedTest
    @MethodSource("data_lengthOfMonth")
    public void test_lengthOfMonth(int year, int month, int length) {
        assertEquals(length, DiscordianDate.of(year, month, 1).lengthOfMonth());
    }
}
