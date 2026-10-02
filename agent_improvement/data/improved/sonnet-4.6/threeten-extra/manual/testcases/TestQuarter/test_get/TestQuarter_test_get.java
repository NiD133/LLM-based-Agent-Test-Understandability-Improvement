package org.threeten.extra;

import static java.time.temporal.IsoFields.QUARTER_OF_YEAR;
import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.stream.Stream;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

public class TestQuarter_test_get {

    // Maps each Quarter enum constant to its expected numeric value (1–4).
    static Stream<Arguments> quarterToExpectedValue() {
        return Stream.of(
            Arguments.of(Quarter.Q1, 1),
            Arguments.of(Quarter.Q2, 2),
            Arguments.of(Quarter.Q3, 3),
            Arguments.of(Quarter.Q4, 4)
        );
    }

    @ParameterizedTest(name = "{0}.get(QUARTER_OF_YEAR) == {1}")
    @MethodSource("quarterToExpectedValue")
    public void test_get(Quarter quarter, int expectedValue) {
        assertEquals(expectedValue, quarter.get(QUARTER_OF_YEAR));
    }
}
