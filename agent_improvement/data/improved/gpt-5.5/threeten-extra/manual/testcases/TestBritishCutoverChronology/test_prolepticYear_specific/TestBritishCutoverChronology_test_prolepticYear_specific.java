package org.threeten.extra.chrono;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.time.chrono.Era;
import java.util.stream.Stream;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

public class TestBritishCutoverChronology_test_prolepticYear_specific {

    private static Stream<Arguments> prolepticYearCases() {
        return Stream.of(
                Arguments.of(JulianEra.AD, 4, 4),
                Arguments.of(JulianEra.AD, 3, 3),
                Arguments.of(JulianEra.AD, 2, 2),
                Arguments.of(JulianEra.AD, 1, 1),
                Arguments.of(JulianEra.BC, 1, 0),
                Arguments.of(JulianEra.BC, 2, -1),
                Arguments.of(JulianEra.BC, 3, -2),
                Arguments.of(JulianEra.BC, 4, -3));
    }

    @ParameterizedTest
    @MethodSource("prolepticYearCases")
    public void test_prolepticYear_specific(Era era, int yearOfEra, int expectedProlepticYear) {
        assertEquals(
                expectedProlepticYear,
                BritishCutoverChronology.INSTANCE.prolepticYear(era, yearOfEra));
    }
}
