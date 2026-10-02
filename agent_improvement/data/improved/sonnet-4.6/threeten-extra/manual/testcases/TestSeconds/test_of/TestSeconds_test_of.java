package org.threeten.extra;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.stream.Stream;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

public class TestSeconds_test_of {

    // Provides (input, expectedAmount) pairs covering zero, positive, negative, and boundary values.
    static Stream<Arguments> secondsValues() {
        return Stream.of(
                Arguments.of(0,                  0),
                Arguments.of(1,                  1),
                Arguments.of(2,                  2),
                Arguments.of(Integer.MAX_VALUE,  Integer.MAX_VALUE),
                Arguments.of(-1,                 -1),
                Arguments.of(-2,                 -2),
                Arguments.of(Integer.MIN_VALUE,  Integer.MIN_VALUE)
        );
    }

    @ParameterizedTest(name = "Seconds.of({0}) should report getAmount() == {1}")
    @MethodSource("secondsValues")
    public void test_of(int input, int expectedAmount) {
        assertEquals(expectedAmount, Seconds.of(input).getAmount());
    }
}
