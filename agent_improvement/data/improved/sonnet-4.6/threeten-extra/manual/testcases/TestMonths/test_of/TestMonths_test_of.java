package org.threeten.extra;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.stream.Stream;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

public class TestMonths_test_of {

    static Stream<Arguments> monthAmounts() {
        return Stream.of(
            Arguments.of(0,                 0),
            Arguments.of(1,                 1),
            Arguments.of(2,                 2),
            Arguments.of(Integer.MAX_VALUE, Integer.MAX_VALUE),
            Arguments.of(-1,                -1),
            Arguments.of(-2,                -2),
            Arguments.of(Integer.MIN_VALUE, Integer.MIN_VALUE)
        );
    }

    @ParameterizedTest
    @MethodSource("monthAmounts")
    public void test_of(int input, int expected) {
        assertEquals(expected, Months.of(input).getAmount());
    }
}
